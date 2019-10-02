package com.lukianbat.test.testmovavi.feature.reddit.domain.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.DBConverter

data class Author(val name: String, val uri: String)

data class RedditRes(val feed: Feed)

@TypeConverters(DBConverter::class)
data class Category(val term: String?, val label: String?)

data class Content(val type: String?, val content: String?)

@Entity(
    tableName = "posts",
    indices = [Index(value = ["date"], unique = false)]
)
@TypeConverters(DBConverter::class)
data class RedditPost(
    val author: Author,
    val link: Link?,
    @PrimaryKey
    val id: String,
    val category: Category?,
    val title: String,
    @SerializedName("updated")
    val date: String,
    val content: Content
) {
    var indexInResponse: Int = -1
}

data class Feed(
    @SerializedName("entry")
    val posts: List<RedditPost>,
    val xmlns: String?,
    val link: List<LinkFeed>?,
    val id: String?,
    val category: Category?,
    val title: String?,
    val updated: String?
)

data class Link(val href: String?)

data class LinkFeed(val rel: String?, val href: String?, val type: String?)
