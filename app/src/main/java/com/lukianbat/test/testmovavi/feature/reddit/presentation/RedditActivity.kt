package com.lukianbat.test.testmovavi.feature.reddit.presentation

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject


class RedditActivity : MvpAppCompatActivity(), RedditView {

    @Inject
    lateinit var daggerPresenter: Lazy<RedditPresenter>

    @ProvidePresenter
    fun providePresenter(): RedditPresenter = daggerPresenter.value

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
