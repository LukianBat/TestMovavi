package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class RedditPresenter : MvpPresenter<RedditView>() {
    init {
        viewState.showMessage("fff")
    }
}