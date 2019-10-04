package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api

import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import retrofit2.Call
import javax.inject.Inject

interface RedditApiDataSource {
    fun getTop(): Call<RedditRes>
    fun getTopAfter(after: String): Call<RedditRes>
    fun getTopBefore(before: String): Call<RedditRes>
}

class RedditApiDataSourceImpl @Inject constructor(private val api: RedditApi) :
    RedditApiDataSource {

    override fun getTop(): Call<RedditRes> = api.getTop(LIMIT)


    override fun getTopAfter(after: String): Call<RedditRes> = api.getTopAfter(after, LIMIT)

    override fun getTopBefore(before: String): Call<RedditRes> = api.getTopBefore(before, LIMIT)

    companion object {
        const val LIMIT = 10
    }
}