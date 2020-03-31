package com.demo.mvvm.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by Siddiqa on 26-01-2020.
 */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    private var mActivity: BaseActivity<*> ?= null
    abstract fun getLayoutId(): Int

    lateinit var mviewDataBinding: ViewDataBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            mActivity = context
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mviewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), container, false)
        return mviewDataBinding.root
    }

    fun getViewDataBinding():V{
        return mviewDataBinding as V
    }
    open fun hideKeyboard() {
        if (mActivity != null) {
            mActivity?.hideKeyboard()
        }
    }
}