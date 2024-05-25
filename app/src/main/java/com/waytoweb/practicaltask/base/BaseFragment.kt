package com.waytoweb.practicaltask.base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun provideMessage(msg: String) {
        (activity as BaseActivity).provideMessage(msg)
    }

    fun loadFragment(fragment: Fragment, isAdd: Boolean, isAddBackStack: Boolean) {
        (activity as BaseActivity).loadFragment(fragment, isAdd, isAddBackStack)
    }
    fun showLoader(){
        (activity as BaseActivity).toggleLoader(true)
    }
    fun hideLoader(){
        (activity as BaseActivity).toggleLoader(false)
    }

    fun goBack() {
            parentFragmentManager.popBackStackImmediate()
    }
}