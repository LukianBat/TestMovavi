package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger

import com.lukianbat.test.testmovavi.core.data.AppDatabase
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditCacheDataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditCacheDataSourceImpl
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditPostDao
import dagger.Module
import dagger.Provides

@Module
class CacheDataSourceModule {

    @Provides
    fun provideDao(database: AppDatabase): RedditPostDao = database.redditEntryDao()

    @Provides
    fun providesDataSource(dao: RedditPostDao): RedditCacheDataSource =
        RedditCacheDataSourceImpl(dao)
}