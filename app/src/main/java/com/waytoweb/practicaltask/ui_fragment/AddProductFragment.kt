package com.waytoweb.practicaltask.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.waytoweb.practicaltask.MainActivity
import com.waytoweb.practicaltask.base.BaseFragment
import com.waytoweb.practicaltask.databinding.AddProductFragmentBinding
import com.waytoweb.practicaltask.network.ApiRequest
import com.waytoweb.practicaltask.network.response.Product
import com.waytoweb.practicaltask.utils.Constants
import com.waytoweb.practicaltask.viewmodel.AppViewModel

class AddProductFragment : BaseFragment() {
    private val appViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }
    lateinit var binding: AddProductFragmentBinding
    lateinit var selectedProduct: Product
    var isEdit=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).binding.appBar.visibility=View.VISIBLE
        getArgs()
        observeResponse()
        setOnClickListener()
    }
    private fun getArgs(){
        if (arguments?.containsKey(Constants.PRODUCT)==true){
            selectedProduct= requireArguments().getSerializable(Constants.PRODUCT) as Product
            binding.editTextTitle.setText(selectedProduct.title)
            binding.buttonAdd.text="Edit"
            isEdit=true
        }
    }

    private fun setOnClickListener() {
        binding.buttonAdd.setOnClickListener {
            if (isEdit){
                appViewModel.editProduct(selectedProduct.id,ApiRequest().apply {
                    title = binding.editTextTitle.text?.trim().toString()
                })
            } else {
                appViewModel.addProduct(ApiRequest().apply {
                    title = binding.editTextTitle.text?.trim().toString()
                })
            }
        }
    }

    private fun observeResponse() {
        appViewModel.addProductResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                parentFragmentManager.popBackStack()
                provideMessage("Product added")
            } else {
                provideMessage("Something went wrong")
            }
        }

        appViewModel.editProductResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                parentFragmentManager.popBackStack()
                provideMessage("Product updated")
            } else {
                provideMessage("Something went wrong")
            }
        }
    }
}