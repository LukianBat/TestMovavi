package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Category
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Entry

@Dao
interface RedditEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: ArrayList<Entry>)

    @Query("SELECT * FROM posts WHERE category = :category ORDER BY indexInResponse ASC")
    fun postsBySubreddit(category: Category): DataSource.Factory<Int, Entry>

    @Query("DELETE FROM posts WHERE category = :category")
    fun deleteBySubreddit(category: Category)

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts WHERE category = :category")
    fun getNextIndexInSubreddit(category: Category): Int
}