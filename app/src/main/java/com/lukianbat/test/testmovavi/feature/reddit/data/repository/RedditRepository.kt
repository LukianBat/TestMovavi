package com.lukianbat.test.testmovavi.feature.reddit.data.repository

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.lukianbat.test.testmovavi.core.utils.Listing
import com.lukianbat.test.testmovavi.core.utils.NetworkState
import com.lukianbat.test.testmovavi.core.utils.RedditBoundaryCallback
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger.RedditCacheDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface RedditRepository {
    fun getPosts(): Listing<RedditPost>
}

class RedditRepositoryImpl @Inject constructor(
    private val apiDataSource: RedditApiDataSource,
    private val cacheDataSource: RedditCacheDataSource
) : RedditRepository {


    override fun getPosts(): Listing<RedditPost> {
        val boundaryCallback = RedditBoundaryCallback(apiDataSource, this::insertResultIntoDb)
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }
        val livePagedList = LivePagedListBuilder(cacheDataSource.getPosts(), LIMIT)
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

    private fun insertResultIntoDb(body: RedditRes) {
        body.feed.posts.let { posts ->
            cacheDataSource.getNextIndexPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe { start ->
                    val items = posts.mapIndexed { index, child ->
                        child.indexInResponse = start + index
                        child
                    }
                    cacheDataSource.insert(items)
                        .subscribeOn(Schedulers.io())
                        .subscribe {}
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        apiDataSource.getTop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                cacheDataSource.deletePosts()
                    .subscribeOn(Schedulers.io()).subscribe {
                        insertResultIntoDb(res)
                        networkState.postValue(NetworkState.LOADED)
                    }
            }, { error ->
                networkState.value = NetworkState.error(error.message)
            })
        return networkState
    }

    companion object {
        const val LIMIT = 10
    }
}