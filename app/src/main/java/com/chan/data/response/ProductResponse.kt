package com.chan.data.response

import com.chan.ui.home.model.ProductModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(

    @field:Json(name = "id")
    val id: String? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "thumbnail")
    val thumbnail: String? = null,

    @field:Json(name = "description")
    val descriptionResponse: DescriptionResponse? = null,

    @field:Json(name = "rate")
    val rate: Float? = null
)

fun ProductResponse?.mapToModel() = this?.let {
    ProductModel(
        id = id ?: "",
        name = name ?: "",
        thumbnail = thumbnail ?: "",
        descriptionModel = descriptionResponse.mapToModel(),
        rate = rate ?: 0.0f
    )
} ?: ProductModel()