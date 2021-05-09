package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceKorona {
    var gson = GsonBuilder().setLenient().create()

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl("https://api.kawalcorona.com/").addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun service() : ApiServiceKawalKorona = getRetrofit().create(ApiServiceKawalKorona::class.java)
}