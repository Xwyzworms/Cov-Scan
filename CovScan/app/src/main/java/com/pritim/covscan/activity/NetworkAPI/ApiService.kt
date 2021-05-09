package com.pritim.covscan.activity.NetworkAPI

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @FormUrlEncoded
    @POST("getDatabyID.php")
    fun getUserByID(@Field("id") id : String) : Call<ResponseGet>

    @FormUrlEncoded
    @POST("insert.php")
    fun insertData( @Field("nama") nama : String,
                    @Field("email") email : String,
                    @Field("password") password : String,
                    @Field("tgl_lahir") tgl_lahir : String) : Call<ResponseAction>

    @FormUrlEncoded
    @POST("updateConfidence.php")
    fun updateConfidence(@Field("id") id : String,
                        @Field("confidence") confidence : Float ) : Call<ResponseAction>

    @FormUrlEncoded
    @POST("deleteByID.php")
    fun deleteData(@Field("id") id : String) : Call<ResponseAction>

    @FormUrlEncoded
    @POST("getDataByEmail.php")
    fun getDataByEmail(@Field("email") email : String) : Call<ResponseGet>

    @FormUrlEncoded
    @POST("updatePassword.php")
    fun updatePassword(@Field("password") password : String,
                       @Field("id")id : String)       :Call<ResponseAction>
}