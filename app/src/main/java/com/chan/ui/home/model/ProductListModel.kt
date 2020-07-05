package com.chan.ui.home.model

data class ProductListModel(
    val totalCount: Int = 0,
    val productList: List<ProductModel> = emptyList()
)
