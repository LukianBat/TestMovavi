package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.lukianbat.test.testmovavi.core.presentation.eventsdispatcher.EventsDispatcher
import com.lukianbat.test.testmovavi.core.presentation.eventsdispatcher.EventsDispatcherOwner
import com.lukianbat.test.testmovavi.core.presentation.viewmodel.BaseViewModel
import com.lukianbat.test.testmovavi.feature.reddit.domain.usecase.GetPostsUseCase
import javax.inject.Inject

class RedditViewModel @Inject constructor(getPostsUseCase: GetPostsUseCase) : BaseViewModel(),
    EventsDispatcherOwner<RedditViewModel.EventsListener> {
    override val eventsDispatcher: EventsDispatcher<EventsListener> = EventsDispatcher()


    interface EventsListener {
        fun showMessage(msg: String)
    }
}