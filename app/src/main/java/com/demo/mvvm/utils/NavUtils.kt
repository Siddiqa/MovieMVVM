package com.demo.mvvm.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by Siddiqa on 26-1-2020.
 */
object NavUtils {

    fun ReplaceFragment(fragment: Fragment,fragmentManager: FragmentManager,id:Int){
        checkNotNull(fragment)
        checkNotNull(fragmentManager)
        var transaction=fragmentManager.beginTransaction()
        transaction.replace(id,fragment)
        transaction.commit()
    }

    fun AddFragment(fragment: Fragment,fragmentManager: FragmentManager,id:Int){
        checkNotNull(fragment)
        checkNotNull(fragmentManager)
        var transaction=fragmentManager.beginTransaction()
        transaction.add(id,fragment)
        transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
    }
}