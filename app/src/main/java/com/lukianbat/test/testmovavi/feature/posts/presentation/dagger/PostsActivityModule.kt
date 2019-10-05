package com.lukianbat.test.testmovavi.feature.posts.presentation.dagger

import androidx.lifecycle.ViewModelProvider
import com.lukianbat.test.testmovavi.core.presentation.viewmodel.ViewModelFactory
import com.lukianbat.test.testmovavi.feature.posts.domain.usecase.GetPostsUseCase
import com.lukianbat.test.testmovavi.feature.posts.presentation.PostsActivity
import com.lukianbat.test.testmovavi.feature.posts.presentation.PostsViewModel
import dagger.Module
import dagger.Provides

@Module
class PostsActivityModule {

    @Provides
    fun providePresenter(
        context: PostsActivity,
        getPostsUseCase: GetPostsUseCase
    ): PostsViewModel = ViewModelFactory {
        PostsViewModel(getPostsUseCase)
    }.let { viewModelFactory ->
        ViewModelProvider(context, viewModelFactory)[PostsViewModel::class.java]
    }

}
