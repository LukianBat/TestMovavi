package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db

import androidx.paging.DataSource
import androidx.room.*
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl

@Dao
interface RedditPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<BasePostImpl>)

    @Query("SELECT * FROM posts ORDER BY indexInResponse ASC")
    fun posts(): DataSource.Factory<Int, BasePostImpl>

    @Query("DELETE FROM posts")
    fun delete()

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts")
    fun getNextIndex(): Int
}