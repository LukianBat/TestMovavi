package com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.core.utils.sortByDate
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import com.lukianbat.test.testmovavi.feature.posts.domain.model.MeduzaRes
import com.lukianbat.test.testmovavi.feature.posts.domain.model.RedditRes
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.helper.PagingRequestHelper
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.helper.createStatusLiveData
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
        val baseList = arrayListOf<BasePost>()
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            webservice.getMeduzaPosts().enqueue(object : Callback<MeduzaRes> {
                override fun onFailure(call: Call<MeduzaRes>, t: Throwable) {
                    it.recordFailure(t)
                }

                override fun onResponse(call: Call<MeduzaRes>, response: Response<MeduzaRes>) {
                    ioExecutor.execute {
                        webservice.getRedditTop().execute().body()?.entries?.let { list ->
                            baseList.addAll(
                                list
                            )
                        }
                        response.body()?.entries?.let { list ->
                            baseList.addAll(
                                list
                            )
                        }
                        insertItemsIntoDb(baseList.sortByDate(), it)
                    }
                }
            })
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
        res: List<BasePostImpl>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(res)
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: BasePostImpl) {
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
                val resList = response.body()?.entries
                resList?.sortByDate()?.let { list -> insertItemsIntoDb(list, it) }
            }
        }
    }
}