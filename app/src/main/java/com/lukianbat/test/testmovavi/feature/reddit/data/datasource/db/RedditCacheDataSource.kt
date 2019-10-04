package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db

import androidx.paging.DataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import javax.inject.Inject

interface RedditCacheDataSource {

    fun insert(posts: List<RedditPost>)
    fun posts(): DataSource.Factory<Int, RedditPost>
    fun delete()
    fun getNextIndex(): Int

}

class RedditCacheDataSourceImpl @Inject constructor(private val dao: RedditPostDao) :
    RedditCacheDataSource {

    override fun posts(): DataSource.Factory<Int, RedditPost> =
        dao.posts()

    override fun delete() = dao.delete()


    override fun getNextIndex(): Int =
        dao.getNextIndex()

    override fun insert(posts: List<RedditPost>) = dao.insert(posts)


}