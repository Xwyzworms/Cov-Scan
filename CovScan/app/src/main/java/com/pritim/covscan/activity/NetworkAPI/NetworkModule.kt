package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    var Gson = GsonBuilder().setLenient().create()
    fun getRetrofit() : Retrofit {

        return Retrofit.Builder().baseUrl("http://192.168.0.113/BackendEhealth/").addConverterFactory(GsonConverterFactory.create(Gson)).build()
    }
    fun service() : ApiService= getRetrofit().create(ApiService::class.java)
}