package com.lukianbat.test.testmovavi.core.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.lukianbat.test.testmovavi.core.utils.NetworkState

data class Listing<T>(

    val pagedList: LiveData<PagedList<T>>,

    val networkState: LiveData<NetworkState>,

    val refreshState: LiveData<NetworkState>,

    val refresh: () -> Unit,

    val retry: () -> Unit
)