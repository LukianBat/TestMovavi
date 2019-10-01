package com.lukianbat.test.testmovavi.feature.reddit.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.DBConverter

data class Author(val name: String?, val uri: String?)

data class RedditRes(val feed: Feed?)

data class Category(val term: String?, val label: String?)

data class Content(val type: String?, val content: String?)

@Entity(
    tableName = "posts",
    indices = [Index(value = ["category"], unique = false)]
)
@TypeConverters(DBConverter::class)
data class Entry(
    val author: Author?,
    val link: Link?,
    val id: String?,
    val category: Category?,
    val title: String?,
    val updated: String?,
    val content: Content?
) {
    var indexInResponse: Int = -1
}

data class Feed(
    val entry: List<Entry>?,
    val xmlns: String?,
    val link: List<LinkFeed>?,
    val id: String?,
    val category: Category?,
    val title: String?,
    val updated: String?
)

data class Link(val href: String?)

data class LinkFeed(val rel: String?, val href: String?, val type: String?)
