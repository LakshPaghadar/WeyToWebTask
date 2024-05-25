package com.waytoweb.practicaltask.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getPlaceHolder():Int
    fun provideMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
    /*---------------------------This block contain load fragment information----------------------*/
    fun loadFragment(fragment: Fragment, isAdd: Boolean, isAddBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isAdd) {
            fragmentTransaction.add(getPlaceHolder(), fragment, fragment::class.java.simpleName)
        } else {
            fragmentTransaction.replace(getPlaceHolder(), fragment, fragment::class.java.simpleName)
        }
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }
        fragmentTransaction.commit()
    }

    internal var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
    }
    fun toggleLoader(show: Boolean) {
        if (show) {
            if (!progressDialog!!.isShowing) progressDialog!!.show()
        } else {
            if (progressDialog!!.isShowing) progressDialog!!.dismiss()
        }
    }
    fun showLoader(){
        toggleLoader(true)
    }
    fun hideLoader(){
        toggleLoader(false)
    }

}