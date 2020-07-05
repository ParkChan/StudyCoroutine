package com.chan.ui.home.domain

import com.chan.network.NetworkResult
import com.chan.network.api.GoodChoiceApi
import com.chan.ui.home.domain.entity.res.MainResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchProductRepository @Inject constructor(
    private val goodChoiceApi: GoodChoiceApi
) : SearchProductRemoteDataSource {

    private val ioDispatcher = Dispatchers.IO

    override suspend fun getProductList(page: Int): NetworkResult<MainResponse> =
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