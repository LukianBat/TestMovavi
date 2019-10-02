package com.lukianbat.test.testmovavi.feature.reddit.presentation.dagger

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import com.lukianbat.test.testmovavi.feature.reddit.presentation.RedditPresenter
import dagger.Module
import dagger.Provides

@Module
class RedditActivityModule {

    @Provides
    @ActivityScope
    fun providePresenter(
        getPostsUseCase: GetPostsUseCase
    ): RedditPresenter = RedditPresenter(getPostsUseCase)

}
