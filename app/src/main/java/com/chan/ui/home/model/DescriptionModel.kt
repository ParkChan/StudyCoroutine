package com.chan.ui.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DescriptionModel(
    val imagePath: String = "",
    val subject: String = "",
    val price: Int = 0
) : Parcelable
