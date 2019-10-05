package com.lukianbat.test.testmovavi.feature.posts.data.datasource.db

import androidx.paging.DataSource
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import javax.inject.Inject

interface PostsCacheDataSource {

    fun insert(posts: List<BasePostImpl>)
    fun posts(): DataSource.Factory<Int, BasePostImpl>
    fun delete()
    fun getNextIndex(): Int

}

class PostsCacheDataSourceImpl @Inject constructor(private val dao: PostsDao) :
    PostsCacheDataSource {

    override fun posts(): DataSource.Factory<Int, BasePostImpl> =
        dao.posts()

    override fun delete() = dao.delete()


    override fun getNextIndex(): Int =
        dao.getNextIndex()

    override fun insert(posts: List<BasePostImpl>) = dao.insert(posts)


}