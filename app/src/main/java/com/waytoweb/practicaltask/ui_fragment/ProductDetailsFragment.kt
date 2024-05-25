package com.waytoweb.practicaltask.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.waytoweb.practicaltask.MainActivity
import com.waytoweb.practicaltask.base.BaseFragment
import com.waytoweb.practicaltask.databinding.ProductDetailsFragmentBinding
import com.waytoweb.practicaltask.utils.Constants
import com.waytoweb.practicaltask.viewmodel.AppViewModel

class ProductDetailsFragment : BaseFragment() {
    lateinit var binding: ProductDetailsFragmentBinding
    private val appViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailsFragmentBinding.inflate(inflater)
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
        if (arguments?.containsKey(Constants.ID)==true){
            callApiAll(requireArguments().getInt(Constants.ID))
        }
    }
    private fun callApiAll(id:Int){
        showLoader()
        appViewModel.getProduct(id)
    }
    private fun observeResponse(){
        appViewModel.getProductResponse.observe(viewLifecycleOwner){
            hideLoader()
            if (it.isSuccessful){
                if (it.body()!=null){
                    var product=it.body()
                    binding.apply {
                        textViewDescValue.text=product?.description
                        textViewTitleValue.text=product?.title
                        textViewCategory.text="Category : "+product?.category
                        textViewPrice.text="Price : "+product?.price.toString()
                        textViewDiscount.text="Discount : "+product?.discountPercentage.toString()
                        textViewRating.text="Rating : "+product?.rating.toString()
                        if (product?.isDeleted!=null){
                            textViewIsDelete.text="Is Delete : "+product?.isDeleted.toString()
                        }
                    }

                }
            } else {
                provideMessage("Something went wrong")
            }
        }
    }
    private fun setOnClickListener(){

    }
}