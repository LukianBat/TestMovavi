package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api

import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RedditApi {
    @GET(URL_TOP)
    fun getTop(
        @Query(LIMIT) limit: Int
    ): Call<RedditRes>


    @GET(URL_TOP)
    fun getTopAfter(
        @Query(AFTER) after: String,
        @Query(LIMIT) limit: Int
    ): Call<RedditRes>

    @GET(URL_TOP)
    fun getTopBefore(
        @Query(BEFORE) before: String,
        @Query(LIMIT) limit: Int
    ): Call<RedditRes>

    companion object {
        const val URL_TOP = "/r/all/.rss?sort=new"
        const val BEFORE = "before"
        const val AFTER = "after"
        const val LIMIT = "limit"
    }
}