package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.arellomobile.mvp.MvpView

interface RedditView : MvpView {
    fun showMessage(message: String)
}