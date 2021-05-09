package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName

data class ResponseGet (
    @field:SerializedName("data")
    val data : MutableList<User> ?= null,

    @field:SerializedName("success")
    val success : String ?= null,

    @field:SerializedName("message")
    val message : String ?= null
)