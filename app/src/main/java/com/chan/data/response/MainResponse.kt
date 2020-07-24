package com.chan.data.response

import com.chan.ui.home.model.MainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainResponse(

    @field:Json(name = "code")
    val code: Int? = null,

    @field:Json(name = "msg")
    val message: String? = null,

    @field:Json(name = "data")
    val data: ProductListResponse? = null
)

fun MainResponse.mapToModel() =
    MainModel(
        code = code ?: 0,
        message = message ?: "",
        data = data.mapToModel()
    )