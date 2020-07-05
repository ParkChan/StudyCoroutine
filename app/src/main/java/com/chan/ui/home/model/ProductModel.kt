package com.chan.ui.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val id: String = "",
    val name: String = "",
    val thumbnail: String = "",
    val descriptionModel: DescriptionModel = DescriptionModel(),
    val rate: Float = 0.0f
) : Parcelable
