package com.lukianbat.test.testmovavi.feature.posts.data.repository.dagger

import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.RedditApiDataSource
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.api.dagger.ApiDataSourceModule
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.dagger.CacheDataSourceModule
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsCacheDataSource
import com.lukianbat.test.testmovavi.feature.posts.data.repository.PostsRepository
import com.lukianbat.test.testmovavi.feature.posts.data.repository.PostsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [ApiDataSourceModule::class, CacheDataSourceModule::class])
class PostsRepositoryModule {

    @Provides
    fun providePostsRepository(
        apiDataSource: RedditApiDataSource,
        cacheDataSource: PostsCacheDataSource
    ): PostsRepository = PostsRepositoryImpl(apiDataSource, cacheDataSource)

}