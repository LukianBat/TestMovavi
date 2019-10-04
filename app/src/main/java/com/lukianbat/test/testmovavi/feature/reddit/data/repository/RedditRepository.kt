package com.lukianbat.test.testmovavi.feature.reddit.data.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.lukianbat.test.testmovavi.core.utils.Listing
import com.lukianbat.test.testmovavi.core.utils.NetworkState
import com.lukianbat.test.testmovavi.core.utils.SubredditBoundaryCallback
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditCacheDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.concurrent.Executors
import javax.inject.Inject

interface RedditRepository {
    fun posts(): Listing<RedditPost>
}

class RedditRepositoryImpl @Inject constructor(
    private val apiDataSource: RedditApiDataSource,
    private val cacheDataSource: RedditCacheDataSource
) : RedditRepository {

    val ioExecutor = Executors.newSingleThreadExecutor()

    private fun sortResultByDate(body: RedditRes?): RedditRes? {
        val postList = body?.entries?.sortedByDescending { it.date }
        return postList?.let { RedditRes(it) }
    }

    private fun insertResultIntoDb(body: RedditRes?) {
        body!!.entries.let { posts ->
            val start = cacheDataSource.getNextIndex()
            posts.forEach {
                Log.i("TAG", "date = "+it.date)
            }
            val items = posts.mapIndexed { index, child ->
                child.indexInResponse = start + index
                child
            }
            cacheDataSource.insert(items)
        }
    }

    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        apiDataSource.getTop().enqueue(
            object : Callback<RedditRes> {
                override fun onFailure(call: Call<RedditRes>, t: Throwable) {
                    // retrofit calls this on main thread so safe to call set value
                    networkState.value = NetworkState.error(t.message)
                }

                override fun onResponse(
                    call: Call<RedditRes>,
                    response: Response<RedditRes>
                ) {
                    ioExecutor.execute {
                        cacheDataSource.delete()
                        insertResultIntoDb(sortResultByDate(response.body()))
                        networkState.postValue(NetworkState.LOADED)
                    }
                }
            }
        )
        return networkState
    }

    override fun posts(): Listing<RedditPost> {

        val boundaryCallback = SubredditBoundaryCallback(
            webservice = apiDataSource,
            handleResponse = this::insertResultIntoDb,
            ioExecutor = ioExecutor
        )
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }
        val livePagedList = LivePagedListBuilder(cacheDataSource.posts(), 10)
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
        const val LIMIT = 10
    }
}