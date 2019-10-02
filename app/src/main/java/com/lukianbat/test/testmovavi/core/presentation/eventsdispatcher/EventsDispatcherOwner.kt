package com.lukianbat.test.testmovavi.core.presentation.eventsdispatcher

interface EventsDispatcherOwner<T> {
    val eventsDispatcher: EventsDispatcher<T>
}