package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditPostDao
import dagger.Module
import dagger.Provides

@Module(includes = [DaoModule::class])
class CacheDataSourceModule {

    @ActivityScope
    @Provides
    fun providesDataSource(dao: RedditPostDao): RedditCacheDataSource =
        RedditCacheDataSourceImpl(dao)
}