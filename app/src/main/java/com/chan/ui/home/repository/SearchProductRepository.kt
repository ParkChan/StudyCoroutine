package com.chan.ui.home.repository

import com.chan.network.NetworkResult
import com.chan.network.api.GoodChoiceApi
import com.chan.ui.home.model.res.ResProductListModel
import com.chan.ui.home.remote.SearchProductRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchProductRepository(
    private val goodChoiceApi: GoodChoiceApi
) : SearchProductRemoteDataSource {

    private val ioDispatcher = Dispatchers.IO

    override suspend fun getProductList(page: Int): NetworkResult<ResProductListModel> =
        withContext(ioDispatcher) {
            return@withContext try {
                NetworkResult.Success(
                    goodChoiceApi.getProductList(page = page)
                )
            } catch (e: Exception) {
                NetworkResult.Failure(e)
            }
        }


}