package com.chan.network.api

import com.chan.ui.home.model.res.ResProductListModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodChoiceApi {

    @GET("App/json/{page}.json")
    suspend fun getProductList(
        @Path("page") page: Int
    ): ResProductListModel
}