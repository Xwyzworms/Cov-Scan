package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IndonesiaKawalAPI (
        @field:SerializedName("name")
        val name : String  ,

        @field:SerializedName("positif")
        val positif : String ,

        @field:SerializedName("sembuh")
        val sembuh : String,

        @field:SerializedName("meninggal")
        val meninggal : String,

        @field:SerializedName("dirawat")
        val dirawat : String)  : Serializable