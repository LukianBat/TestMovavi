package com.lukianbat.test.testmovavi.core

import com.lukianbat.test.testmovavi.core.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent
            .builder()
            .context(this)
            .create(this)

    companion object {
        const val TOPIC = "all"
    }
}