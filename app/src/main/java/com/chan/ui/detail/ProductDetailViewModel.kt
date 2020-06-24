package com.chan.ui.detail

import android.content.Context
import com.chan.common.base.BaseViewModel
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.home.model.ProductModel

class ProductDetailViewModel(
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    fun isBookMark(
        context: Context,
        productModel: ProductModel,
        onResult: (isBookMark: Boolean) -> Unit
    ) {
        compositeDisposable.add(bookmarkRepository.selectExists(
            context,
            productModel,
            result = { exists ->
                onResult(exists)
            }
        ))

    }

    fun onClickBookMark(context: Context, productModel: ProductModel) {
        isBookMark(context, productModel, onResult = {
            if (it) {
                compositeDisposable.add(
                    bookmarkRepository.deleteBookMark(
                        context,
                        productModel
                    )
                )
            } else {
                compositeDisposable.add(
                    bookmarkRepository.insertBookMark(
                        context,
                        productModel
                    )
                )
            }

        })
    }
}