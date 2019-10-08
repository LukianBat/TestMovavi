package com.lukianbat.test.testmovavi.feature.posts.data.datasource.db

import androidx.paging.DataSource
import androidx.room.*
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<BasePostImpl>)

    @Query("SELECT * FROM posts ORDER BY date DESC")
    fun posts(): DataSource.Factory<Int, BasePostImpl>

    @Query("DELETE FROM posts")
    fun delete()
}