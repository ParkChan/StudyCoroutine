package com.chan.ui.home.domain

import com.chan.network.NetworkResult
import com.chan.ui.home.domain.entity.res.MainResponse

interface SearchProductRemoteDataSource {

    suspend fun getProductList(
        page: Int
    ): NetworkResult<MainResponse>
}