package com.lukianbat.test.testmovavi.core.dagger

import android.content.Context
import com.lukianbat.test.testmovavi.core.App
import com.lukianbat.test.testmovavi.core.dagger.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        @BindsInstance
        abstract fun context(context: Context): Builder
    }
}