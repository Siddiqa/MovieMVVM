package com.demo.mvvm.ui

import android.os.Bundle
import com.demo.mvvm.R
import com.demo.mvvm.base.BaseActivity
import com.demo.mvvm.databinding.ActivityMainBinding
import com.demo.mvvm.utils.NavUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavUtils.ReplaceFragment(HomeFragment(), supportFragmentManager,
            R.id.maincontainer
        )


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
