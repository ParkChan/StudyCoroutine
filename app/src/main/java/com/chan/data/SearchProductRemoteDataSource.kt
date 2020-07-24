package com.chan.data

import com.chan.data.response.MainResponse
import com.chan.network.NetworkResult

interface SearchProductRemoteDataSource {

    suspend fun getProductList(
        page: Int
    ): NetworkResult<MainResponse>
}