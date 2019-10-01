package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Entry

class DBConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun toEntryList(str: String): ArrayList<Entry> {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Entry>>() {}.type
            return gson.fromJson(str, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromEntryList(list: ArrayList<Entry>): String {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Entry>>() {}.type
            return gson.toJson(list, type)
        }
    }
}