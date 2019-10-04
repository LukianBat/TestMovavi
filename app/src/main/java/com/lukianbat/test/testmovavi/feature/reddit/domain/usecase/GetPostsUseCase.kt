package com.lukianbat.test.testmovavi.feature.reddit.domain.usecase

import com.lukianbat.test.testmovavi.core.utils.Listing
import com.lukianbat.test.testmovavi.feature.reddit.data.repository.RedditRepository
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.RedditPost
import javax.inject.Inject

interface GetPostsUseCase {
    fun getPosts(): Listing<RedditPost>
}

class GetPostsUseCaseImpl @Inject constructor(private val repository: RedditRepository) :
    GetPostsUseCase {

    override fun getPosts(): Listing<RedditPost> = repository.posts()

}