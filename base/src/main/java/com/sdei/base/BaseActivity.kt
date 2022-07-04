package com.sdei.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<dataBinding : ViewDataBinding> : AppCompatActivity() {

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    abstract var binding: dataBinding

    lateinit var mViewDataBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mViewDataBinding=DataBindingUtil.setContentView(this, layoutId)
        onCreate()
    }

    /**
     * already called in onCreate method of activity
     */
    protected abstract fun onCreate()

    fun setUpBinding(): dataBinding {
        return mViewDataBinding as dataBinding
    }
}