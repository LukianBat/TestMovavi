package com.lukianbat.test.testmovavi.core.dagger.module.feature

import com.lukianbat.test.testmovavi.core.dagger.scope.ActivityScope
import com.lukianbat.test.testmovavi.feature.reddit.domain.dagger.GetPostsUseCaseModule
import com.lukianbat.test.testmovavi.feature.reddit.presentation.PostsActivity
import com.lukianbat.test.testmovavi.feature.reddit.presentation.dagger.PostsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [GetPostsUseCaseModule::class])
interface RedditModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostsActivityModule::class])
    fun redditActivityInjector(): PostsActivity
}