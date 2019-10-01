package com.lukianbat.test.testmovavi.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditEntryDao
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Entry

@Database(
    entities = [Entry::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditEntryDao(): RedditEntryDao
}