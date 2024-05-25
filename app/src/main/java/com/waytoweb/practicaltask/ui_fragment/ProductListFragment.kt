package com.waytoweb.practicaltask.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waytoweb.practicaltask.MainActivity
import com.waytoweb.practicaltask.adapter.ProductAdapter
import com.waytoweb.practicaltask.base.BaseFragment
import com.waytoweb.practicaltask.databinding.ProductListFragmentBinding
import com.waytoweb.practicaltask.network.ApiRequest
import com.waytoweb.practicaltask.network.response.Product
import com.waytoweb.practicaltask.utils.Constants
import com.waytoweb.practicaltask.utils.EndlessRecyclerViewScrollListener
import com.waytoweb.practicaltask.viewmodel.AppViewModel

class ProductListFragment : BaseFragment() {
    lateinit var binding: ProductListFragmentBinding
    lateinit var productAdapter: ProductAdapter
    var page = 1
    var notifyPos=-1
    var limit=10
    var skip=0
    private val appViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).binding.appBar.visibility=View.GONE
        callApiAll(page)
        setAdapter()
        observeResponse()
        setOnClickListener()
    }

    private fun setAdapter() {
        productAdapter = ProductAdapter(
            callbackForClick = object : (Int) -> Unit {
                override fun invoke(p1: Int) {
                    val bundle = Bundle().apply {
                        putInt(Constants.ID, p1)
                    }
                    val productDetailsFragment = ProductDetailsFragment()
                    productDetailsFragment.arguments = bundle
                    loadFragment(productDetailsFragment, false, true)
                }
            },
            callbackForEdit = object : (Product, Int) -> Unit {
                override fun invoke(p1: Product,pos:Int) {
                    val bundle = Bundle().apply {
                        putSerializable(Constants.PRODUCT, p1)
                        putBoolean(Constants.IS_EDIT,true)
                    }
                    val addProductFragment = AddProductFragment()
                    addProductFragment.arguments = bundle
                    loadFragment(addProductFragment,false,true)
                }
            },
            callbackForDelete = object : (Int,Int) -> Unit {
                override fun invoke(p1: Int,pos:Int) {
                    notifyPos=pos
                    appViewModel.deleteProduct(p1)
                }
            }
        )
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                this@ProductListFragment.page = page
                callApiAll(page)
            }
        })
    }

    private fun callApiAll(page: Int) {
        showLoader()
        if (page!=1){
            skip += 10
        }
        appViewModel.getProductPageList(limit, skip)
        //appViewModel.getProductList(ApiRequest().apply { })
    }

    private fun setOnClickListener() {
        binding.buttonAdd.setOnClickListener {
            loadFragment(AddProductFragment(),false,true)
        }
        binding.buttonSearch.setOnClickListener {
            if(binding.editTextSearch.text?.trim().toString().isBlank()){
                provideMessage("Please enter text which you want to search ")
            } else {
                appViewModel.searchProducts(binding.editTextSearch.text?.trim().toString())
            }
        }
    }

    private fun observeResponse() {
        appViewModel.deleteProductResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                productAdapter.list.removeAt(notifyPos)
                productAdapter.notifyItemRemoved(notifyPos)
                provideMessage("Deleted Successfully")
            } else {
                provideMessage("Something went wrong")
            }
        }
        //powder
        appViewModel.searchProductListResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                if (it.body()?.products != null) {
                    it.body()?.products as ArrayList
                    productAdapter.list.clear()
                    productAdapter.list.addAll(it.body()?.products!!)
                    productAdapter.notifyDataSetChanged()
                }
            } else {
                provideMessage("Something went wrong")
            }
        }
        appViewModel.productListResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                if (it.body()?.products != null) {
                    it.body()?.products as ArrayList
                    productAdapter.list.clear()
                    productAdapter.list.addAll(it.body()?.products!!)
                    productAdapter.notifyDataSetChanged()
                }
            } else {
                provideMessage("Something went wrong")
            }
        }
        appViewModel.productPageListResponse.observe(viewLifecycleOwner) {
            hideLoader()
            if (it.isSuccessful) {
                if (it.body()?.products != null) {
                    it.body()?.products as ArrayList
                    productAdapter.list.addAll(it.body()?.products!!)
                    productAdapter.notifyDataSetChanged()
                }
            } else {
                provideMessage("Something went wrong")
            }
        }
    }
}