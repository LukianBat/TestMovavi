package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    @ActivityScope
    fun provideRedditApi(retrofit: Retrofit): RedditApi = retrofit.create(RedditApi::class.java)

}