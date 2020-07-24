package com.chan.ui.home.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chan.common.base.BaseViewModel
import com.chan.data.SearchProductRepository
import com.chan.data.response.mapToModel
import com.chan.db.DataBaseResult
import com.chan.network.NETWORK_ROW_COUNT
import com.chan.network.NetworkResult
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.detail.ProductDetailContractData
import com.chan.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val searchProductRepository: SearchProductRepository,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    private val defaultTotalPageCnt = 1
    private val defaultStartPageNumber = 1

    private var requestePage = defaultTotalPageCnt
    private var totalPage = defaultTotalPageCnt
    private var isProgress = false

    private val _productListData = MutableLiveData<List<ProductModel>>()
    val productListModel: LiveData<List<ProductModel>> get() = _productListData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _productItemSelected = MutableLiveData<ProductDetailContractData>()
    val productItemSelected get() = _productItemSelected


    fun listScrolled(visibleItemCount: Int, fistVisibleItem: Int, totalItemCount: Int) {
        if (viewModelScope.isActive) {
            if (visibleItemCount + fistVisibleItem >= totalItemCount) {
                if (!isProgress && requestePage <= totalPage) {
                    requestListData(false)
                }
            }
        }
    }

    fun requestListData(isFirstPage: Boolean) = viewModelScope.launch {

        isProgress = true

        if (isFirstPage) {
            initPageInfo()
        }

        val resProductListModelDeferred =
            async { searchProductRepository.getProductList(requestePage) }

        when (val networkResult = resProductListModelDeferred.await()) {
            is NetworkResult.Success -> {

                networkResult.data.mapToModel().run {
                    if (isFirstPage) {
                        val totalCount = data.totalCount
                        totalPage = if (totalCount / NETWORK_ROW_COUNT > 0) {
                            (totalCount / NETWORK_ROW_COUNT) + 1
                        } else {
                            defaultTotalPageCnt
                        }
                    }
                    _productListData.value = data.productList
                    requestePage++
                    isProgress = false
                }

            }
            is NetworkResult.Failure -> {
                _errorMessage.value = networkResult.exception.message ?: ""
                isProgress = false
            }
        }
    }

    private fun initPageInfo() {
        requestePage = defaultStartPageNumber
        totalPage = defaultTotalPageCnt
    }

    //상품 상세화면으로 이동
    fun startProductDetailActivity(position: Int, productModel: ProductModel) {
        _productItemSelected.value = ProductDetailContractData(position, productModel)
    }

    fun isBookMark(
        context: Context,
        productModel: ProductModel,
        onResult: (isBookMark: Boolean) -> Unit
    ) = viewModelScope.launch {
        val bookmarkResult = async {
            bookmarkRepository.isExists(context, productModel)
        }

        when (val dbResult = bookmarkResult.await()) {
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