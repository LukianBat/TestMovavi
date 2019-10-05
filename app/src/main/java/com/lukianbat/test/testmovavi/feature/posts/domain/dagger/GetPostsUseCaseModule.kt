package com.lukianbat.test.testmovavi.feature.posts.domain.dagger

import com.lukianbat.test.testmovavi.feature.posts.data.repository.PostsRepository
import com.lukianbat.test.testmovavi.feature.posts.data.repository.dagger.PostsRepositoryModule
import com.lukianbat.test.testmovavi.feature.posts.domain.usecase.GetPostsUseCase
import com.lukianbat.test.testmovavi.feature.posts.domain.usecase.GetPostsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module(includes = [PostsRepositoryModule::class])
class GetPostsUseCaseModule {

    @Provides
    fun bindsGetPostsUseCase(repository: PostsRepository): GetPostsUseCase =
        GetPostsUseCaseImpl(repository)
}