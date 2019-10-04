package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api

import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RedditApi {
    @GET("/.rss")
    fun getTop(
        @Query("limit") limit: Int
    ): Call<RedditRes>


    @GET("/.rss")
    fun getTopAfter(
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): Call<RedditRes>

    @GET("/.rss")
    fun getTopBefore(
        @Query("before") before: String,
        @Query("limit") limit: Int
    ): Call<RedditRes>
}