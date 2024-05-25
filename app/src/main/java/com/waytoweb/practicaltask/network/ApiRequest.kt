package com.waytoweb.practicaltask.network

import com.google.gson.annotations.SerializedName

class ApiRequest(
    @SerializedName("username")
    var userName:String?=null,

    @SerializedName("page")
    var page:String?=null,

    @SerializedName("title")
    var title:String?=null,

    @SerializedName("password")
    var password:String?=null

) {
}