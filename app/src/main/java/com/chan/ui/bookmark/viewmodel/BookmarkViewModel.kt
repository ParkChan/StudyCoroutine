package com.chan.ui.bookmark.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chan.common.base.BaseViewModel
import com.chan.db.DataBaseResult
import com.chan.ui.bookmark.BookmarkSortType
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.detail.ProductDetailContractData
import com.chan.ui.home.model.DescriptionModel
import com.chan.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookmarkViewModel @ViewModelInject constructor(
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    private val _bookmarkListData = MutableLiveData<List<BookmarkModel>>()
    val bookmarkListData: LiveData<List<BookmarkModel>> get() = _bookmarkListData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _removeBookmarkModel = MutableLiveData<BookmarkModel>()
    val removeBookmarkModel: LiveData<BookmarkModel> get() = _removeBookmarkModel

    private val _existsProductModel = MutableLiveData<ProductModel>()
    val existsProductModel: LiveData<ProductModel> get() = _existsProductModel

    private val _productItemSelected = MutableLiveData<ProductDetailContractData>()
    val productItemSelected get() = _productItemSelected

    //정렬 타입
    private val _sortType = MutableLiveData<BookmarkSortType>().apply {
        value = BookmarkSortType.RegDateDesc
    }
    val sortType: LiveData<BookmarkSortType> get() = _sortType

    var lastRequestSortType: BookmarkSortType = BookmarkSortType.RegDateDesc

    fun selectAll(context: Context, sortType: BookmarkSortType) = viewModelScope.launch {

        val bookmarkResult =
            async { bookmarkRepository.selectBookmarkList(context, sortType) }

        when (val dbResult = bookmarkResult.await()) {
            is DataBaseResult.Success -> {
                lastRequestSortType = sortType
                _bookmarkListData.postValue(dbResult.data)
            }
            is DataBaseResult.Failure -> {
                _errorMessage.postValue(dbResult.exception.message ?: "")
            }
        }
    }

    fun sendRemoveBookmarkData(model: BookmarkModel) {
        _removeBookmarkModel.postValue(model)
    }

    fun removeBookmark(context: Context, model: BookmarkModel) = viewModelScope.launch {
        bookmarkRepository.deleteBookMark(context, model)
        selectAll(context, lastRequestSortType)
    }

    fun isBookmarkCanceled(context: Context, productModel: ProductModel) = viewModelScope.launch {
        val bookmarkResultDefferred = async {
            bookmarkRepository.isExists(context, productModel)
        }
        when (val dbResult = bookmarkResultDefferred.await()) {
            is DataBaseResult.Success -> if (!dbResult.data) {
                _existsProductModel.value = productModel
            }
            is DataBaseResult.Failure -> Logger.d(dbResult.exception.message ?: "")
        }
    }

    //상품 상세화면으로 이동
    fun startProductDetailActivity(position: Int, model: BookmarkModel) {
        ProductModel(
            id = model.id,
            name = model.name,
            thumbnail = model.thumbnail,
            descriptionModel = DescriptionModel(
                imagePath = model.imagePath,
                subject = model.subject,
                price = model.price
            ),
            rate = model.rate
        ).run {
            _productItemSelected.value = ProductDetailContractData(position, this)
        }
    }
}