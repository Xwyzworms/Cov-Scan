package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName

class ResponseAction {

    @field:SerializedName("message")
    val message : String ?= null

    @field:SerializedName("success")
    val success : String ?= null
}