package com.chan.network.api

import com.chan.data.response.MainResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodChoiceApi {

    @GET("App/json/{page}.json")
    suspend fun getProductList(
        @Path("page") page: Int
    ): MainResponse
}