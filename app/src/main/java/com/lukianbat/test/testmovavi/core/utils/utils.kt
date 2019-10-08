package com.lukianbat.test.testmovavi.core.utils

import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import java.text.SimpleDateFormat
import java.util.*

const val MEDUZA_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"
const val REDDIT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

fun BasePost.toBaseImpl(): BasePostImpl =
    BasePostImpl(this.author, this.id, this.title, this.date, this.content, this.image)


fun List<BasePost>.toBaseImpl(): List<BasePostImpl> = this.map {
    it.toBaseImpl()
}

fun List<BasePost>.convertDate(): List<BasePostImpl> {
    this.forEach {
        val date: String
        date = if (it.id == "") {
            val format = SimpleDateFormat(MEDUZA_DATE_FORMAT, Locale.ENGLISH)
            format.parse(it.date).toString()
        } else {
            val format = SimpleDateFormat(REDDIT_DATE_FORMAT, Locale.ENGLISH)
            format.parse(it.date).toString()
        }
        it.date = date
    }
    return this.toBaseImpl()
}
