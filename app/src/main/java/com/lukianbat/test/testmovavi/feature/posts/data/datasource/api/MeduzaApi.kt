package com.lukianbat.test.testmovavi.feature.posts.data.datasource.api

import com.lukianbat.test.testmovavi.feature.posts.domain.model.MeduzaRes
import retrofit2.Call
import retrofit2.http.GET

interface MeduzaApi {
    @GET(URL)
    fun getPosts(): Call<MeduzaRes>

    companion object {
        const val URL = "/rss/podcasts/meduza-v-kurse"
    }
}