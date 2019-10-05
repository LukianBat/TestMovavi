package com.lukianbat.test.testmovavi.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukianbat.test.testmovavi.feature.posts.data.datasource.db.PostsDao
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl

@Database(
    entities = [BasePostImpl::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditEntryDao(): PostsDao
}