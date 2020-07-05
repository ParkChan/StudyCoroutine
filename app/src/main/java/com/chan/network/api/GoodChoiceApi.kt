package com.chan.network.api

import com.chan.ui.home.domain.entity.res.MainResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodChoiceApi {

    @GET("App/json/{page}.json")
    suspend fun getProductList(
        @Path("page") page: Int
    ): MainResponse
}