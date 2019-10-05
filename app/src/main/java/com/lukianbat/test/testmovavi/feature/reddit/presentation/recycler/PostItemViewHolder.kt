package com.lukianbat.test.testmovavi.feature.reddit.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukianbat.test.testmovavi.R
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePost

class PostItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val date: TextView = view.findViewById(R.id.date)
    private val content: TextView = view.findViewById(R.id.content)
    private val author: TextView = view.findViewById(R.id.author)
    private var post: BasePost? = null
    private var check = false

    init {
        view.setOnClickListener {
            if (check.not()) {
                content.visibility = View.VISIBLE
                check = true
            } else {
                content.visibility = View.GONE
                check = false
            }
        }
    }

    fun bind(post: BasePost?) {
        this.post = post
        title.text = post?.title ?: "loading"
        content.text = post?.content ?: "unknown"
        author.text = post?.author ?: ""
        date.text = post?.date ?: "unknown"
    }

    companion object {
        fun create(parent: ViewGroup): PostItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_post_item, parent, false)
            return PostItemViewHolder(view)
        }
    }

    fun updateScore(item: BasePost?) {
        post = item
    }
}