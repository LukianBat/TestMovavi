package com.lukianbat.test.testmovavi.feature.reddit.domain.usecase

import com.lukianbat.test.testmovavi.feature.reddit.domain.recycler.boundary.Listing
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepository
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.BasePostImpl
import javax.inject.Inject

interface GetPostsUseCase {
    fun getPosts(): Listing<BasePostImpl>
}

class GetPostsUseCaseImpl @Inject constructor(private val repository: RedditRepository) :
    GetPostsUseCase {

    override fun getPosts(): Listing<BasePostImpl> = repository.posts()

}