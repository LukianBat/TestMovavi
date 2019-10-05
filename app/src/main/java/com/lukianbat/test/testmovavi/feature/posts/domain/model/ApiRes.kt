package com.lukianbat.test.testmovavi.feature.posts.domain.model

import androidx.room.*
import org.simpleframework.xml.*

interface BasePost {
    var author: String
    var id: String
    var title: String
    var date: String
    var content: String
    var image: String
    var indexInResponse: Int
}

@Entity(tableName = "posts")
class BasePostImpl(
    override var author: String,
    override var id: String = "",
    @PrimaryKey override var title: String,
    override var date: String,
    override var content: String,
    override var image: String = ""
) : BasePost {
    override var indexInResponse: Int = -1
}

@Root(name = "entry", strict = false)
class RedditPost(
    @field:Path("author")
    @param:Path("author")
    @field:Element(name = "name")
    @param:Element(name = "name")
    override var author: String,
    @field:Element(name = "id")
    @param:Element(name = "id")
    override var id: String,
    @field:Element(name = "title")
    @param:Element(name = "title")
    override var title: String,
    @field:Element(name = "updated")
    @param:Element(name = "updated")
    override var date: String,
    @field:Element(name = "content")
    @param:Element(name = "content")
    override var content: String
) : BasePost {
    override var indexInResponse: Int = -1
    override var image: String = ""
}

@Root(name = "item", strict = false)
data class MeduzaPost(
    @field:Element(name = "title")
    @param:Element(name = "title")
    override var title: String,
    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    override var date: String = "",
    @field:Element(name = "encoded")
    @param:Element(name = "encoded")
    override var content: String
) : BasePost {
    override var author: String = "meduza"
    override var image: String = ""
    override var id: String = ""
    override var indexInResponse: Int = -1
}

@Root(name = "rss", strict = false)
data class MeduzaRes(
    @field:Path("channel")
    @param:Path("channel")
    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    var entries: List<MeduzaPost>
)

@Root(name = "feed", strict = false)
data class RedditRes(
    @field:ElementList(name = "entry", inline = true)
    @param:ElementList(name = "entry", inline = true)
    var entries: List<RedditPost>
)