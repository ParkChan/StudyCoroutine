package com.chan.ui.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DescriptionModel(

    @SerializedName("imagePath")
    val imagePath: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("price")
    val price: Int

) : Parcelable
