package com.lukianbat.test.testmovavi.feature.reddit.domain.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepository
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.dagger.RedditRepositoryModule
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RedditRepositoryModule::class])
class GetPostsUseCaseModule {

    @Provides
    fun bindsGetPostsUseCase(repository: RedditRepository): GetPostsUseCase =
        GetPostsUseCaseImpl(repository)
}