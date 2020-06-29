package com.chan.ui.detail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.chan.common.base.BaseViewModel
import com.chan.ui.bookmark.local.DataBaseResult
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    fun isBookMark(
        context: Context,
        productModel: ProductModel,
        onResult: (isBookMark: Boolean) -> Unit
    ) = viewModelScope.launch {
        val bookmarkReulstReffered = async {
            bookmarkRepository.isExists(context, productModel)
        }

        when (val dbResult = bookmarkReulstReffered.await()) {
            is DataBaseResult.Success -> onResult(dbResult.data)
            is DataBaseResult.Failure -> Logger.d(dbResult.exception.message ?: "")
        }
    }

    fun onClickBookMark(context: Context, productModel: ProductModel) {
        isBookMark(context, productModel, onResult = {
            viewModelScope.launch {
                if (it) {
                    bookmarkRepository.deleteBookMark(
                        context,
                        convertToBookMarkModel(productModel)
                    )
                } else {
                    bookmarkRepository.insertBookMark(
                        context,
                        convertToBookMarkModel(productModel)
                    )
                }
            }
        })
    }

    private fun convertToBookMarkModel(model: ProductModel): BookmarkModel {
        return BookmarkModel(
            id = model.id,
            name = model.name,
            thumbnail = model.thumbnail,
            imagePath = model.descriptionModel.imagePath,
            subject = model.descriptionModel.subject,
            price = model.descriptionModel.price,
            rate = model.rate,
            regTimeStamp = System.currentTimeMillis()
        )
    }
}