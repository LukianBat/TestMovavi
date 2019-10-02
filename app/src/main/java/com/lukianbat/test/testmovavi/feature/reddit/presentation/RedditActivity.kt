package com.lukianbat.test.testmovavi.feature.reddit.presentation

import com.lukianbat.test.testmovavi.BR
import com.lukianbat.test.testmovavi.R
import com.lukianbat.test.testmovavi.core.presentation.activity.EventsActivity
import com.lukianbat.test.testmovavi.databinding.ActivityRedditBinding
import javax.inject.Inject

class RedditActivity :
    EventsActivity<ActivityRedditBinding, RedditViewModel, RedditViewModel.EventsListener>(),
    RedditViewModel.EventsListener {
    override fun showMessage(msg: String) {

    }

    override val eventsListener: RedditViewModel.EventsListener = this
    override val viewModelVariableId: Int = BR.viewModel
    @Inject
    override lateinit var viewModel: RedditViewModel

    override val layoutId = R.layout.activity_reddit
}
