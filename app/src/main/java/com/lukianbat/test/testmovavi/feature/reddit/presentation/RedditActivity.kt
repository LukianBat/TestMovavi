package com.lukianbat.test.testmovavi.feature.reddit.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.lukianbat.test.testmovavi.R
import dagger.Lazy
import dagger.android.AndroidInjection
import javax.inject.Inject


class RedditActivity : MvpAppCompatActivity(), RedditView {

    @Inject
    lateinit var daggerPresenter: Lazy<RedditPresenter>

    @InjectPresenter
    lateinit var presenter: RedditPresenter

    @ProvidePresenter
    fun providePresenter(): RedditPresenter = daggerPresenter.get()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
