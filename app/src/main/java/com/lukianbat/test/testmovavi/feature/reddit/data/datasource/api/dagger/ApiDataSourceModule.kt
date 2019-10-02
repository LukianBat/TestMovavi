package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApi
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSourceImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiDataSourceModule {

    @Provides
    fun provideRedditApi(retrofit: Retrofit): RedditApi = retrofit.create(RedditApi::class.java)

    @Provides
    fun providesDataSource(api: RedditApi): RedditApiDataSource = RedditApiDataSourceImpl(api)
}