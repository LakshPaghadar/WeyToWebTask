package com.waytoweb.practicaltask.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.waytoweb.practicaltask.MainActivity
import com.waytoweb.practicaltask.base.BaseFragment
import com.waytoweb.practicaltask.databinding.LoginFragmentBinding
import com.waytoweb.practicaltask.network.ApiRequest
import com.waytoweb.practicaltask.viewmodel.AppViewModel

class LoginFragment : BaseFragment() {
    lateinit var binding: LoginFragmentBinding
    private val appViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //observeResponse()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).binding.appBar.visibility=View.VISIBLE
        observeResponse()
        setOnClickListener()
    }

    private fun observeResponse() {
        appViewModel.loginResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                provideMessage("Login Success")
                loadFragment(ProductListFragment(),false,false)
            } else {
                provideMessage(it.errorBody().toString())
            }
        }
    }

    private fun setOnClickListener() {
        binding.buttonLogin.setOnClickListener {
            if (validate()) {
                showLoader()
                var apiRequest = ApiRequest().apply {
                    userName = binding.editTextUsername.text?.trim().toString()
                    password = binding.editTextPassword.text?.trim().toString()
                }
                appViewModel.login(request = apiRequest)

            }
        }
    }

    private fun validate(): Boolean {
        if (binding.editTextUsername.text?.trim().toString().isBlank()) {
            binding.textFieldUsername.setError("Please enter username")
            return false
        }
        if (binding.editTextPassword.text?.trim().toString().isBlank()) {
            binding.textFieldPassword.setError("Please enter password")
            return false
        }
        return true
    }
}