package com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.dagger

import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.MeduzaApi
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApi
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApiDataSourceImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class ApiDataSourceModule {

    @Named(REDDIT_API)
    @Provides
    fun provideRedditApi(@Named(REDDIT_RETROFIT) retrofit: Retrofit): RedditApi =
        retrofit.create(RedditApi::class.java)

    @Named(MEDUZA_API)
    @Provides
    fun provideMeduzaApi(@Named(MEDUZA_RETROFIT) retrofit: Retrofit): MeduzaApi =
        retrofit.create(MeduzaApi::class.java)

    @Provides
    fun providesDataSource(
        @Named(REDDIT_API)
        redditApi: RedditApi,
        @Named(MEDUZA_API)
        meduzaApi: MeduzaApi
    ): RedditApiDataSource = RedditApiDataSourceImpl(redditApi, meduzaApi)

    companion object {
        const val REDDIT_API = "redditApi"
        const val REDDIT_RETROFIT = "redditRetrofit"
        const val MEDUZA_API = "meduzaApi"
        const val MEDUZA_RETROFIT = "meduzaRetrofit"
    }
}