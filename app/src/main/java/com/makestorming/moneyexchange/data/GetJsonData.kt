package com.makestorming.moneyexchange.data

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetJsonData{

/*    @GET("r/app_bind.json")
    fun call(): Call<JsonObject?>?*/

    @GET("latest?symbols=USD,GBP")
    fun call(): Call<JsonObject?>?

/*    @GET("users/{user}/repos")
    fun getUserRepositories(@Path("user") userName: String?): Call<JsonArray?>?*/

//    @GET("latest?symbols={user}")
    @GET("latest")
    fun callData(@Query("symbols") userName: String?): Call<JsonObject?>?

}