package com.lukianbat.test.testmovavi.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditPostDao
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl

@Database(
    entities = [BasePostImpl::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditEntryDao(): RedditPostDao
}