package com.lukianbat.test.testmovavi.feature.posts.presentation

import androidx.lifecycle.ViewModel
import com.lukianbat.test.testmovavi.feature.posts.domain.usecase.GetPostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(getPostsUseCase: GetPostsUseCase) : ViewModel() {

    private val repoResult = getPostsUseCase.getPosts()
    val posts = repoResult.pagedList
    val networkState = repoResult.networkState
    val refreshState = repoResult.refreshState

    fun refresh() {
        repoResult.refresh.invoke()
    }

    fun retry() {
        val listing = repoResult
        listing.retry.invoke()
    }
}