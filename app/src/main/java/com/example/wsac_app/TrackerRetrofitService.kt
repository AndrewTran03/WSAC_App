package com.example.wsac_app

import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TrackerRetrofitService {

    @POST(value= MainActivity.ROUTE)
    fun postLog(@Body json: JSONObject): Call<String>

    companion object {
        fun create(baseUrl: String): TrackerRetrofitService {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())).baseUrl(baseUrl).build()
            return retrofit.create(TrackerRetrofitService::class.java)
        }
    }
}