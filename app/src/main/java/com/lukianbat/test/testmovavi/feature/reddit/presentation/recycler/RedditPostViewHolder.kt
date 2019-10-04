package com.lukianbat.test.testmovavi.feature.reddit.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.test.testmovavi.R
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost

class RedditPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)
    private var post: RedditPost? = null

    init {
        view.setOnClickListener {
            Toast.makeText(it.context, post?.id, Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(post: RedditPost?) {
        this.post = post
        title.text = post?.title ?: "loading"
        subtitle.text = post?.date ?: "unknown"
    }

    companion object {
        fun create(parent: ViewGroup): RedditPostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_post_item, parent, false)
            return RedditPostViewHolder(view)
        }
    }

    fun updateScore(item: RedditPost?) {
        post = item
    }
}