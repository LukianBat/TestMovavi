package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Category
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RedditPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<RedditPost>): Completable

    @Query("SELECT * FROM posts WHERE date = :date ORDER BY indexInResponse ASC")
    fun postsByDate(date: String): DataSource.Factory<Int, RedditPost>

    @Query("SELECT * FROM posts ORDER BY indexInResponse ASC")
    fun getPosts(): DataSource.Factory<Int, RedditPost>

    @Query("DELETE FROM posts")
    fun deletePosts() : Completable

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts")
    fun getNextIndexInPosts(): Single<Int>

    @Query("DELETE FROM posts WHERE date = :date")
    fun deleteByDate(date: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts WHERE date = :date")
    fun getNextIndexInDate(date: String): Int
}