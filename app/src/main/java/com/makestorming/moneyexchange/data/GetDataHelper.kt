package com.makestorming.moneyexchange.data

import com.google.gson.JsonObject
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetDataHelper {

    lateinit var callback: Callback<JsonObject?>

    fun setCallBack(callback: Callback<JsonObject?>){
        this.callback = callback
    }

    companion object{
        const val BASE_URL = "https://api.exchangeratesapi.io/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GetJsonData::class.java)

    fun start() {
        val request = service.callData("USD,GBP")
        request?.enqueue(callback)
    }



}