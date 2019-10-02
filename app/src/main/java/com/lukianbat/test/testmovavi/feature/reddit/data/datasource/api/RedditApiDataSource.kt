package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api

import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditRes
import io.reactivex.Single
import javax.inject.Inject

interface RedditApiDataSource {
    fun getTop(): Single<RedditRes>
    fun getTopAfter(after: String): Single<RedditRes>
    fun getTopBefore(before: String): Single<RedditRes>
}

class RedditApiDataSourceImpl @Inject constructor(private val api: RedditApi) :
    RedditApiDataSource {

    override fun getTop(): Single<RedditRes> = api.getTop(LIMIT)


    override fun getTopAfter(after: String): Single<RedditRes> = api.getTopAfter(after, LIMIT)

    override fun getTopBefore(before: String): Single<RedditRes> = api.getTopBefore(before, LIMIT)

    companion object {
        const val LIMIT = 10
    }
}