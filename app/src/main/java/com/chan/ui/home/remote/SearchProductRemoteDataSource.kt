package com.chan.ui.home.remote

import com.chan.network.NetworkResult
import com.chan.ui.home.model.res.ResProductListModel

interface SearchProductRemoteDataSource {

    suspend fun getProductList(
        page: Int
    ): NetworkResult<ResProductListModel>
}