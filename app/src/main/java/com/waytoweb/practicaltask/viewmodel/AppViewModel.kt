package com.waytoweb.practicaltask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waytoweb.practicaltask.network.ApiRequest
import com.waytoweb.practicaltask.network.RetrofitProvider
import com.waytoweb.practicaltask.network.response.Product
import com.waytoweb.practicaltask.network.response.ProductWrapper
import kotlinx.coroutines.launch
import retrofit2.Response

class AppViewModel:ViewModel() {

    val loginResponse=MutableLiveData<Response<Any>>()

    fun login(request: ApiRequest){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.login(request)
            loginResponse.postValue(response)
        }
    }

    val productListResponse=MutableLiveData<Response<ProductWrapper>>()
    fun getProductList(request: ApiRequest){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.getProductList(/*request*/)
            productListResponse.postValue(response)
        }
    }

    val productPageListResponse=MutableLiveData<Response<ProductWrapper>>()
    fun getProductPageList(limit:Int,skip:Int){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.getProductPageList(limit, skip)
            productPageListResponse.postValue(response)
        }
    }

    val searchProductListResponse=MutableLiveData<Response<ProductWrapper>>()
    fun searchProducts(search: String){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.searchProducts(search)
            searchProductListResponse.postValue(response)
        }
    }

    val getProductResponse=MutableLiveData<Response<Product>>()
    fun getProduct(id1: Int){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.getProduct(id =id1 )
            getProductResponse.postValue(response)
        }
    }

    val deleteProductResponse=MutableLiveData<Response<Product>>()
    fun deleteProduct(id1: Int){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.deleteProduct(id =id1 )
            deleteProductResponse.postValue(response)
        }
    }

    val addProductResponse=MutableLiveData<Response<Product>>()
    fun addProduct(request: ApiRequest){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.addProduct(request )
            addProductResponse.postValue(response)
        }
    }

    val editProductResponse=MutableLiveData<Response<Product>>()
    fun editProduct(id:Int,request: ApiRequest){
        viewModelScope.launch {
            val response= RetrofitProvider.provideRetrofitObject().apiService.editProduct(id,request)
            editProductResponse.postValue(response)
        }
    }
}