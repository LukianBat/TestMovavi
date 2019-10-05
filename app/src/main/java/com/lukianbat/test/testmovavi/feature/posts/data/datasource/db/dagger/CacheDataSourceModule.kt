package com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.dagger

import com.lukianbat.test.testmovavi.core.data.AppDatabase
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsCacheDataSource
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsCacheDataSourceImpl
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsDao
import dagger.Module
import dagger.Provides

@Module
class CacheDataSourceModule {

    @Provides
    fun provideDao(database: AppDatabase): PostsDao = database.redditEntryDao()

    @Provides
    fun providesDataSource(dao: PostsDao): PostsCacheDataSource =
        PostsCacheDataSourceImpl(dao)
}