package com.norbert.balazs.sliidechallangeapp.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    private var _layout: V? = null

    protected val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = DataBindingUtil.setContentView(this, getLayoutId())
    }

    override fun onDestroy() {
        super.onDestroy()
        _layout = null
    }

    protected abstract fun getLayoutId(): Int
}