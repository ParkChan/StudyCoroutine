package com.chan.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.chan.BR
import com.chan.R
import com.chan.common.base.BaseActivity
import com.chan.databinding.ActivityProductDetailBinding

/**
 * 상품 상세화면
 */
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(
    R.layout.activity_product_detail
) {

    private val productDetailViewModel by viewModels<ProductDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<ProductDetailContractData>(
            ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY
        )?.let {
            binding.setVariable(BR.productModel, it.productModel)
        }

        initViewModel()
        initLayoutComponent()

    }

    private fun initViewModel() {
        binding.productDetailViewModel = productDetailViewModel
    }

    private fun initLayoutComponent() {
        val model = binding.productModel
        model?.run {
            binding.productDetailViewModel?.isBookMark(binding.root.context, model,
                onResult = {
                    binding.tbBookmark.isChecked = it
                })
        }

    }

    override fun onBackPressed() {
        intent.getParcelableExtra<ProductDetailContractData>(
            ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY
        )?.let {
            setResult(
                Activity.RESULT_OK,
                Intent().apply {
                    putExtra(
                        ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY,
                        it
                    )
                })
            finish()
        }
    }
}