package com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.boundary

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.helper.PagingRequestHelper
import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.helper.createStatusLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class SubredditBoundaryCallback(
    private val webservice: RedditApiDataSource,
    private val handleResponse: (List<BasePostImpl>?) -> Unit,
    private val ioExecutor: Executor
) : PagedList.BoundaryCallback<BasePostImpl>() {

    val helper =
        PagingRequestHelper(
            ioExecutor
        )
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            webservice.getRedditTop()
                .enqueue(createWebserviceCallback(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: BasePostImpl) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            webservice.getRedditTopAfter(
                after = itemAtEnd.id
            )
                .enqueue(createWebserviceCallback(it))
        }
    }

    private fun insertItemsIntoDb(
        response: Response<RedditRes>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            val res = response.body()?.entries?.map {
                BasePostImpl(it.author, it.id, it.title, it.date, it.content, it.image)
            }
            handleResponse(res)
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: BasePostImpl) {
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