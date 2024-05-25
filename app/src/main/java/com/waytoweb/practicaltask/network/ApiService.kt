package com.waytoweb.practicaltask.network


import com.waytoweb.practicaltask.network.response.Product
import com.waytoweb.practicaltask.network.response.ProductWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: ApiRequest): Response<Any>

    @GET("products")
    suspend fun getProductList(/*@Body request: ApiRequest*/): Response<ProductWrapper>

    @GET("products")
    suspend fun getProductPageList(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): Response<ProductWrapper>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>


    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductWrapper>

    @POST("products/add")
    suspend fun addProduct(@Body request: ApiRequest): Response<Product>

    @PUT("products/{id}")
    suspend fun editProduct(@Path("id") id: Int, @Body request: ApiRequest): Response<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Product>

}