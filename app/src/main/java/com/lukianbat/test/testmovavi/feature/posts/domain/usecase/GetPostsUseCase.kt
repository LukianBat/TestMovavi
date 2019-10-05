package com.lukianbat.test.testmovavi.feature.posts.domain.usecase

import com.lukianbat.test.testmovavi.feature.posts.domain.recycler.boundary.Listing
import com.lukianbat.test.testmovavi.feature.posts.data.repository.PostsRepository
import com.lukianbat.test.testmovavi.feature.posts.domain.model.BasePostImpl
import javax.inject.Inject

interface GetPostsUseCase {
    fun getPosts(): Listing<BasePostImpl>
}

class GetPostsUseCaseImpl @Inject constructor(private val repository: PostsRepository) :
    GetPostsUseCase {

    override fun getPosts(): Listing<BasePostImpl> = repository.posts()

}