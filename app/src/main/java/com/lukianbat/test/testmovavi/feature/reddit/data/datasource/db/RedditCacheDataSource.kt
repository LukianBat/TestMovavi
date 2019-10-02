package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.dagger

import androidx.paging.DataSource
import com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db.RedditPostDao
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface RedditCacheDataSource {

    fun insert(posts: List<RedditPost>): Completable
    fun getPosts(): DataSource.Factory<Int, RedditPost>
    fun deletePosts(): Completable
    fun getNextIndexPosts(): Single<Int>

}

class RedditCacheDataSourceImpl @Inject constructor(private val dao: RedditPostDao) :
    RedditCacheDataSource {
    override fun insert(posts: List<RedditPost>): Completable =
        dao.insert(posts)

    override fun getPosts(): DataSource.Factory<Int, RedditPost> =
        dao.getPosts()

    override fun deletePosts(): Completable = dao.deletePosts()


    override fun getNextIndexPosts(): Single<Int> = dao.getNextIndexInPosts()


}