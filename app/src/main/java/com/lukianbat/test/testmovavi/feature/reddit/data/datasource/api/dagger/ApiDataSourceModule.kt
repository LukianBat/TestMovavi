package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApi
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSourceImpl
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class])
class ApiDataSourceModule {

    @ActivityScope
    @Provides
    fun providesDataSource(api: RedditApi): RedditApiDataSource = RedditApiDataSourceImpl(api)
}