package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.*

class DBConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun toAuthor(str: String): Author {
            val gson = Gson()
            val type = object : TypeToken<Author>() {}.type
            return gson.fromJson(str, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromAuthor(author: Author?): String {
            val gson = Gson()
            val type = object : TypeToken<Author>() {}.type
            return gson.toJson(author, type)
        }

        @TypeConverter
        @JvmStatic
        fun toCategory(str: String): Category {
            val gson = Gson()
            val type = object : TypeToken<Category>() {}.type
            return gson.fromJson(str, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromCategory(category: Category): String {
            val gson = Gson()
            val type = object : TypeToken<Category>() {}.type
            return gson.toJson(category, type)
        }
    }
}