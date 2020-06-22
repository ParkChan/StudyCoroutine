package com.chan.ui.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("description")
    val descriptionModel: DescriptionModel,

    @SerializedName("rate")
    val rate: Float
) : Parcelable
