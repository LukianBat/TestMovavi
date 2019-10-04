package com.lukianbat.test.testmovavi.core.dagger.module

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named

@Module
class RetrofitModule {

    private val httpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Named(REDDIT_RETROFIT)
    fun providesRedditRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(REDDIT_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Named(MEDUZA_RETROFIT)
    fun providesMeduzaRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MEDUZA_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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