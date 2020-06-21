package com.chan.common.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
    }

    fun showToast(msg: String?) = Toast.makeText(
        this,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}