package com.lukianbat.test.testmovavi.feature.reddit.data.repository.dagger

import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.api.dagger.ApiDataSourceModule
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger.CacheDataSourceModule
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepository
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ApiDataSourceModule::class, CacheDataSourceModule::class])
interface RedditRepositoryModule {

    @Binds
    fun bindsRedditRepository(impl: RedditRepositoryImpl): RedditRepository

}