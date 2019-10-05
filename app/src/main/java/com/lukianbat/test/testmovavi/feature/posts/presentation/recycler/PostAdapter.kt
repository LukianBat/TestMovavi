package com.lukianbat.test.testmovavi.feature.posts.presentation.recycler

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.test.testmovavi.R
import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.NetworkState
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl

class PostsAdapter(
    private val retryCallback: () -> Unit
) : PagedListAdapter<BasePostImpl, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private var networkState: NetworkState? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.reddit_post_item -> (holder as PostItemViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
                networkState
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as PostItemViewHolder).updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.reddit_post_item -> PostItemViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.reddit_post_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<BasePostImpl>() {
            override fun areContentsTheSame(oldItem: BasePostImpl, newItem: BasePostImpl): Boolean =
                oldItem.equals(newItem)

            override fun areItemsTheSame(oldItem: BasePostImpl, newItem: BasePostImpl): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: BasePostImpl, newItem: BasePostImpl): Any? {
                return null
            }
        }

    }
}