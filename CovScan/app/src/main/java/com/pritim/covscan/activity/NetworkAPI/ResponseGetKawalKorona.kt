package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName

data class ResponseGetKawalKorona (
    @field:SerializedName("name")
    val name : String ?= null,

    @field:SerializedName("value")
    val total : String ?= null
        )

