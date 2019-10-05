package com.lukianbat.test.testmovavi.feature.posts.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.BR
import com.lukianbat.test.testmovavi.R
import com.lukianbat.test.testmovavi.core.presentation.activity.BaseActivity
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.NetworkState
import com.lukianbat.test.testmovavi.databinding.ActivityPostsBinding
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import com.lukianbat.test.testmovavi.feature.posts.presentation.recycler.PostsAdapter
import kotlinx.android.synthetic.main.activity_posts.*
import javax.inject.Inject

class PostsActivity :
    BaseActivity<ActivityPostsBinding, PostsViewModel>() {

    override val viewModelVariableId: Int = BR.viewModel
    @Inject
    override lateinit var viewModel: PostsViewModel

    override val layoutId = R.layout.activity_posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        val adapter = PostsAdapter {
            viewModel.retry()
        }
        list.adapter = adapter
        viewModel.posts.observe(this, Observer<PagedList<BasePostImpl>> {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        viewModel.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
