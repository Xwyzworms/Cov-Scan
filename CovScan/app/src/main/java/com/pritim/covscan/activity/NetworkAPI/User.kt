package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @field:SerializedName("id")
    var id : String = "unknown",
    @field:SerializedName("nama")
    var nama : String="unknown",

    @field:SerializedName("password")
    var password: String = "123456",

    @field:SerializedName("email")
    var email : String ?= null,

    @field:SerializedName("tgl_lahir")
    var tgl_lahir : String ?= "00-00-0000",

    @field:SerializedName("confidience")
    var confidence : Float ?= null
        ) : Serializable
