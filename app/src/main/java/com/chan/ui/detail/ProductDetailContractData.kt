package com.chan.ui.detail

import android.os.Parcelable
import com.chan.ui.home.model.ProductModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDetailContractData(
    val position: Int,
    val productModel: ProductModel
) : Parcelable