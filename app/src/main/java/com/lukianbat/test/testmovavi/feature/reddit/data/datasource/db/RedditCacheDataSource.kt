package com.lukianbat.test.testmovavi.feature.reddit.data.datasource.db

import androidx.paging.DataSource
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePost
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl
import javax.inject.Inject

interface RedditCacheDataSource {

    fun insert(posts: List<BasePostImpl>)
    fun posts(): DataSource.Factory<Int, BasePostImpl>
    fun delete()
    fun getNextIndex(): Int

}

class RedditCacheDataSourceImpl @Inject constructor(private val dao: RedditPostDao) :
    RedditCacheDataSource {

    override fun posts(): DataSource.Factory<Int, BasePostImpl> =
        dao.posts()

    override fun delete() = dao.delete()


    override fun getNextIndex(): Int =
        dao.getNextIndex()

    override fun insert(posts: List<BasePostImpl>) = dao.insert(posts)


}