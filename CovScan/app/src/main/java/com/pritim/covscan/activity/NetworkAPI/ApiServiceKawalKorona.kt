package com.pritim.covscan.activity.NetworkAPI

import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceKawalKorona {

    @GET("positif")
    fun getPositifGlobal() : Call<ResponseGetKawalKorona>

    @GET("meninggal")
    fun getMeninggalGlobal() : Call<ResponseGetKawalKorona>

    @GET("sembuh")
    fun getSembuhGlobal() : Call<ResponseGetKawalKorona>

    @GET("indonesia")
    fun getDataIndonesia() : Call<List<IndonesiaKawalAPI>>
}