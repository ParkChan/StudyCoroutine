package com.chan.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ProductDetailActivityContract :
    ActivityResultContract<ProductDetailContractData, ProductDetailContractData>() {

    companion object {
        const val EXTRA_PRODUCT_DATA_KEY = "EXTRA_PRODUCT_DATA_KEY"
    }

    override fun createIntent(
        context: Context,
        productDetailContractData: ProductDetailContractData?
    ): Intent {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(
            EXTRA_PRODUCT_DATA_KEY,
            productDetailContractData
        )
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ProductDetailContractData? {
        return when (resultCode) {
            Activity.RESULT_OK ->
            intent?.getParcelableExtra<ProductDetailContractData>(EXTRA_PRODUCT_DATA_KEY)
            else -> null
        }
    }
}