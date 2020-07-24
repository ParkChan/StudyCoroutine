package com.chan.data.response

import com.chan.ui.home.model.ProductListModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListResponse(

    @field:Json(name = "totalCount")
    val totalCount: Int? = null,

    @field:Json(name = "product")
    val productList: List<ProductResponse>? = null
)

fun ProductListResponse?.mapToModel() = this?.let {
    ProductListModel(
        totalCount = totalCount ?: 0,
        productList = productList?.map { it.mapToModel() } ?: emptyList()
    )
} ?: ProductListModel()
