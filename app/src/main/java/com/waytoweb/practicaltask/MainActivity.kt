package com.waytoweb.practicaltask

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.waytoweb.practicaltask.base.BaseActivity
import com.waytoweb.practicaltask.databinding.ActivityMainBinding
import com.waytoweb.practicaltask.ui_fragment.LoginFragment

class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    override fun getPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(LoginFragment(), isAdd = true, isAddBackStack = false)
        binding.imageViewBack.setOnClickListener {
            fragmentManager.popBackStack()
        }
    }
}