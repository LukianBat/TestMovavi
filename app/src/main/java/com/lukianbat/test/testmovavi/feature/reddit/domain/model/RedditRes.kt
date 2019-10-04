package com.lukianbat.test.testmovavi.feature.reddit.domain.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.DBConverter
import org.simpleframework.xml.*


@Root(name = "author", strict = false)
data class Author(
    @field:Element(name = "name")
    @param:Element(name = "name")
    var name: String,
    @field:Element(name = "uri")
    @param:Element(name = "uri")
    var uri: String
)

@Root(name = "Category", strict = false)
data class Category(
    @field:Attribute(name = "term")
    @param:Attribute(name = "term")
    var term: String
)

@TypeConverters(DBConverter::class)
@Entity(tableName = "posts")
@Root(name = "entry", strict = false)
data class RedditPost(
    @field:Element(name = "category")
    @param:Element(name = "category")
    var category: Category,
    @field:Element(name = "author")
    @param:Element(name = "author")
    var author: Author,
    @PrimaryKey
    @field:Element(name = "id")
    @param:Element(name = "id")
    var id: String,
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String,
    @field:Element(name = "updated")
    @param:Element(name = "updated")
    var date: String,
    @field:Element(name = "content")
    @param:Element(name = "content")
    var content: String
) {
    var indexInResponse: Int = -1
}

@Root(name = "feed", strict = false)
data class RedditRes(
    @field:ElementList(name = "entry", inline = true)
    @param:ElementList(name = "entry", inline = true)
    var entries: List<RedditPost>
)
