package com.lukianbat.test.testmovavi.feature.reddit.data.repository.dagger

import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.dagger.ApiDataSourceModule
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger.CacheDataSourceModule
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditCacheDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepository
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [ApiDataSourceModule::class, CacheDataSourceModule::class])
class RedditRepositoryModule {

    @Provides
    fun provideRedditRepository(
        apiDataSource: RedditApiDataSource,
        cacheDataSource: RedditCacheDataSource
    ): RedditRepository = RedditRepositoryImpl(apiDataSource, cacheDataSource)

}