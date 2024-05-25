package com.waytoweb.practicaltask.network.response

data class ProductWrapper(
    var products:ArrayList<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
) {
}