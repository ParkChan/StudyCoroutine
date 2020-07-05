package com.chan.ui.home.model

data class MainModel(
    val code: Int = 0,
    val message: String = "",
    val data: ProductListModel = ProductListModel()
)

