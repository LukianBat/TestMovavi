package com.lukianbat.test.testmovavi.feature.reddit.presentation.dagger

import androidx.lifecycle.ViewModelProvider
import com.lukianbat.test.testmovavi.core.presentation.viewmodel.ViewModelFactory
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import com.lukianbat.test.testmovavi.feature.reddit.presentation.RedditActivity
import com.lukianbat.test.testmovavi.feature.reddit.presentation.RedditViewModel
import dagger.Module
import dagger.Provides

@Module
class RedditActivityModule {

    @Provides
    fun providePresenter(
        context: RedditActivity,
        getPostsUseCase: GetPostsUseCase
    ): RedditViewModel = ViewModelFactory {
        RedditViewModel(getPostsUseCase)
    }.let { viewModelFactory ->
        ViewModelProvider(context, viewModelFactory)[RedditViewModel::class.java]
    }

}
