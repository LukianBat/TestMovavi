package com.lukianbat.test.testmovavi.feature.reddit.data.repository

import com.lukianbat.test.testmovavi.core.domain.Listing
import com.lukianbat.test.testmovavi.feature.reddit.domain.model.Entry

interface RedditRepository {
    fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<Entry>
}

class RedditRepositoryImpl() : RedditRepository {

    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<Entry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}