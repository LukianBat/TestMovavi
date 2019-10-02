package com.lukianbat.test.testmovavi.core.utils

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class RedditBoundaryCallback(
    private val dataSource: RedditApiDataSource,
    private val handleResponse: (RedditRes) -> Unit
) : PagedList.BoundaryCallback<RedditPost>() {

    private val ioExecutor: Executor = Executors.newSingleThreadExecutor()

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            dataSource.getTop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        insertItemsIntoDb(res, it)
                    }, { error ->
                        it.recordFailure(error)
                    }
                )
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            dataSource.getTopAfter(after = itemAtEnd.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    insertItemsIntoDb(res, it)
                }, { error ->
                    it.recordFailure(error)
                })
        }
    }


    private fun insertItemsIntoDb(
        response: RedditRes,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            response.let { res -> handleResponse(res) }
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
        // ignored, since we only ever append to what's in the DB
    }
}