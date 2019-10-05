package com.lukianbat.test.testmovavi.feature.posts.data.datasource.api

import com.lukianbat.test.testmovavi.feature.posts.domain.model.MeduzaRes
import com.lukianbat.test.testmovavi.feature.posts.domain.model.RedditRes
import retrofit2.Call
import javax.inject.Inject

interface RedditApiDataSource {
    fun getRedditTop(): Call<RedditRes>
    fun getRedditTopAfter(after: String): Call<RedditRes>
    fun getRedditTopBefore(before: String): Call<RedditRes>
    fun getMeduzaPosts(): Call<MeduzaRes>
}

class RedditApiDataSourceImpl @Inject constructor(
    private val redditApi: RedditApi,
    private val meduzaApi: MeduzaApi
) :
    RedditApiDataSource {
    override fun getMeduzaPosts(): Call<MeduzaRes> = meduzaApi.getPosts()


    override fun getRedditTop(): Call<RedditRes> = redditApi.getTop(LIMIT)


    override fun getRedditTopAfter(after: String): Call<RedditRes> =
        redditApi.getTopAfter(after, LIMIT)

    override fun getRedditTopBefore(before: String): Call<RedditRes> =
        redditApi.getTopBefore(before, LIMIT)

    companion object {
        const val LIMIT = 20
    }
}