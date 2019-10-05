package com.lukianbat.test.testmovavi.feature.posts.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.lukianbat.test.testmovavi.core.utils.sortByDate
import com.lukianbat.test.testmovavi.core.utils.toBaseImpl
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.Listing
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.NetworkState
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.SubredditBoundaryCallback
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsCacheDataSource
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import com.lukianbat.test.testmovavi.feature.posts.domain.model.MeduzaRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject

interface PostsRepository {
    fun posts(): Listing<BasePostImpl>
}

class PostsRepositoryImpl @Inject constructor(
    private val apiDataSource: RedditApiDataSource,
    private val cacheDataSource: PostsCacheDataSource
) : PostsRepository {

    val ioExecutor = Executors.newSingleThreadExecutor()


    private fun insertResultIntoDb(list: List<BasePostImpl>?) {
        list?.let { posts ->
            val start = cacheDataSource.getNextIndex()
            val items = posts.mapIndexed { index, child ->
                child.indexInResponse = start + index
                child.toBaseImpl()
            }
            cacheDataSource.insert(items)
        }
    }

    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        val baseList = arrayListOf<BasePost>()
        networkState.value = NetworkState.LOADING
        apiDataSource.getMeduzaPosts().enqueue(
            object : Callback<MeduzaRes> {
                override fun onFailure(call: Call<MeduzaRes>, t: Throwable) {
                    networkState.value = NetworkState.error(t.message)
                }

                override fun onResponse(
                    call: Call<MeduzaRes>,
                    meduzaResponse: Response<MeduzaRes>
                ) {
                    ioExecutor.execute {
                        cacheDataSource.delete()
                        meduzaResponse.body()?.entries?.let { list ->
                            baseList.addAll(
                                list
                            )
                        }
                        apiDataSource.getRedditTop().execute().body()?.entries?.let { list ->
                            baseList.addAll(
                                list
                            )
                        }
                        insertResultIntoDb(baseList.sortByDate())
                        networkState.postValue(NetworkState.LOADED)
                    }
                }
            }
        )
        return networkState
    }

    override fun posts(): Listing<BasePostImpl> {

        val boundaryCallback =
            SubredditBoundaryCallback(
                webservice = apiDataSource,
                handleResponse = this::insertResultIntoDb,
                ioExecutor = ioExecutor
            )
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }
        val livePagedList = LivePagedListBuilder(cacheDataSource.posts(), LIMIT)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            retry = {
                boundaryCallback.helper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState
        )
    }

    companion object {
        const val LIMIT = 20
    }
}