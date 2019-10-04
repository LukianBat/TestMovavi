package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.lukianbat.test.testmovavi.core.presentation.eventsdispatcher.EventsDispatcher
import com.lukianbat.test.testmovavi.core.presentation.eventsdispatcher.EventsDispatcherOwner
import com.lukianbat.test.testmovavi.core.presentation.viewmodel.BaseViewModel
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(getPostsUseCase: GetPostsUseCase) : BaseViewModel(),
    EventsDispatcherOwner<PostsViewModel.EventsListener> {
    override val eventsDispatcher: EventsDispatcher<EventsListener> = EventsDispatcher()

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

    interface EventsListener {
        fun showMessage(msg: String)
    }
}