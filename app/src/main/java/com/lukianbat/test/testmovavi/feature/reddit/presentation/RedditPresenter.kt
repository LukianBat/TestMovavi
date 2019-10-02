package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import javax.inject.Inject

@InjectViewState
class RedditPresenter @Inject constructor(getPostsUseCase: GetPostsUseCase) :
    MvpPresenter<RedditView>() {
    init {
        viewState.showMessage("fff")
    }
}