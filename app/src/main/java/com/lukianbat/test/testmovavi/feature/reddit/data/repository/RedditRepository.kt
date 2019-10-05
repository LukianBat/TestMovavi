package com.lukianbat.test.testmovavi.feature.reddit.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.lukianbat.test.testmovavi.core.utils.sortByDate
import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.boundary.Listing
import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.boundary.NetworkState
import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.boundary.SubredditBoundaryCallback
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditCacheDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.MeduzaRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject

interface RedditRepository {
    fun posts(): Listing<BasePostImpl>
}

class RedditRepositoryImpl @Inject constructor(
    private val apiDataSource: RedditApiDataSource,
    private val cacheDataSource: RedditCacheDataSource
) : RedditRepository {

    val ioExecutor = Executors.newSingleThreadExecutor()


    private fun insertResultIntoDb(list: List<BasePostImpl>?) {
        list?.let { posts ->
            val start = cacheDataSource.getNextIndex()
            val items = posts.mapIndexed { index, child ->
                child.indexInResponse = start + index
                BasePostImpl(
                    child.author,
                    child.id,
                    child.title,
                    child.date,
                    child.content,
                    child.image
                )
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