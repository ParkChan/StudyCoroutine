package com.chan.ui.home.domain.entity

import com.chan.ui.home.model.DescriptionModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescriptionResponse(

    @field:Json(name = "imagePath")
    val imagePath: String? = null,

    @field:Json(name = "subject")
    val subject: String? = null,

    @field:Json(name = "price")
    val price: Int? = null

)

fun DescriptionResponse?.mapToModel() = this?.let {
    DescriptionModel(
        imagePath = imagePath ?: "",
        subject = subject ?: "",
        price = price ?: 0
    )
} ?: DescriptionModel()
