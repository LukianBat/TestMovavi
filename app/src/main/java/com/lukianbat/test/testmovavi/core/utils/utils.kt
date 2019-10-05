package com.lukianbat.test.testmovavi.core.utils

import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl
import java.text.SimpleDateFormat
import java.util.*

const val BASE_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy"
const val MEDUZA_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"
const val REDDIT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

fun List<BasePost>.toBaseImpl(): List<BasePostImpl> = this.map {
    BasePostImpl(it.author, it.id, it.title, it.date, it.content, it.image)
}

fun List<BasePost>.sortByDate(): List<BasePostImpl> {
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
    return this.toBaseImpl().sortedByDescending {
        val format = SimpleDateFormat(BASE_DATE_FORMAT, Locale.ENGLISH)
        val date = format.parse(it.date)
        date
    }
}
