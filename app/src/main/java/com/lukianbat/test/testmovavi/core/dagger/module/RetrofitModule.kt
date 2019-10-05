package com.lukianbat.test.testmovavi.core.dagger.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named

@Module
class RetrofitModule {

    private val httpClient = OkHttpClient.Builder()
        .build()

    @Provides
    @Named(REDDIT_RETROFIT)
    fun providesRedditRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(REDDIT_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Named(MEDUZA_RETROFIT)
    fun providesMeduzaRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MEDUZA_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build()

    companion object {
        const val REDDIT_URL = "https://www.reddit.com/"
        const val MEDUZA_URL = "https://meduza.io/"
        const val REDDIT_RETROFIT = "redditRetrofit"
        const val MEDUZA_RETROFIT = "meduzaRetrofit"

    }
}