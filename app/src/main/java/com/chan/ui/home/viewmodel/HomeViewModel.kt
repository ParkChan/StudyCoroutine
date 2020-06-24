package com.chan.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chan.common.base.BaseViewModel
import com.chan.network.NETWORK_ROW_COUNT
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.detail.ProductDetailContractData
import com.chan.ui.home.model.ProductModel
import com.chan.ui.home.repository.GoodChoiceRepository
import kotlinx.coroutines.isActive

class HomeViewModel(
    private val goodChoiceRepository: GoodChoiceRepository,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    private val defaultTotalPageCnt = 1
    private val defaultStartPageNumber = 1

    private var requestePage = defaultTotalPageCnt
    private var totalPage = defaultTotalPageCnt
    private var isProgress = false

    private val _productListData = MutableLiveData<List<ProductModel>>()
    val productListData: LiveData<List<ProductModel>> get() = _productListData

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

    fun requestListData(isFirstPage: Boolean) {
        if (isFirstPage) {
            initPageInfo()
        }
        isProgress = true
        compositeDisposable.add(
            goodChoiceRepository.requestData(
                defaultStartPageNumber,
                onSuccess = {
                    if (isFirstPage) {
                        val totalCount = it.data.totalCount
                        totalPage = if (totalCount / NETWORK_ROW_COUNT > 0) {
                            (totalCount / NETWORK_ROW_COUNT) + 1
                        } else {
                            defaultTotalPageCnt
                        }
                    }
                    _productListData.value = it.data.productList
                    requestePage++
                    isProgress = false
                },
                onFail = {
                    _errorMessage.value = it
                    isProgress = false
                }
            )
        )
    }

    private fun initPageInfo() {
        requestePage = defaultTotalPageCnt
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
    ) {
        compositeDisposable.add(
            bookmarkRepository.selectExists(
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