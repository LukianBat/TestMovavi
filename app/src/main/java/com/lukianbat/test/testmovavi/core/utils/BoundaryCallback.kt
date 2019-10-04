package com.lukianbat.test.testmovavi.core.utils

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.reflect.KFunction2

class SubredditBoundaryCallback(
    private val webservice: RedditApiDataSource,
    private val handleResponse: (RedditRes?) -> Unit,
    private val ioExecutor: Executor
) : PagedList.BoundaryCallback<RedditPost>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            webservice.getTop()
                .enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            webservice.getTopAfter(
                after = itemAtEnd.id
            )
                .enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
        response: Response<RedditRes>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(response.body())
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun sortResultByDate(body: RedditRes?): RedditRes? {
        val postList = body?.entries?.sortedByDescending { it.date }
        return postList?.let { RedditRes(it) }
    }


    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback)
            : Callback<RedditRes> {
        return object : Callback<RedditRes> {
            override fun onFailure(call: Call<RedditRes>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(
                call: Call<RedditRes>,
                response: Response<RedditRes>
            ) {
                val res = Response.success(sortResultByDate(response.body()))
                insertItemsIntoDb(res, it)
            }
        }
    }
}