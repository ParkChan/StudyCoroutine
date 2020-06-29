package com.chan.network.api

import com.chan.BuildConfig
import com.chan.network.BASE_URL
import com.chan.network.MockInterceptor
import com.chan.ui.home.model.res.ResProductListModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GoodChoiceApi {

    @GET("App/json/{page}.json")
    suspend fun getProductList(
        @Path("page") page: Int
    ): ResProductListModel

    companion object {
        fun create(): GoodChoiceApi {
            val logger = HttpLoggingInterceptor()
            logger.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(MockInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoodChoiceApi::class.java)
        }
    }

}