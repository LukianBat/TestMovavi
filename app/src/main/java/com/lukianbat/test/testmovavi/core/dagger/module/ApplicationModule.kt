package com.lukianbat.test.testmovavi.core.dagger.module

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        RetrofitModule::class,
        RoomModule::class
    ]
)
interface ApplicationModule