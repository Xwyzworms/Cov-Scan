package com.pritim.covscan.activity.NetworkAPI

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseGetKawalIndonesia (
        val data : IndonesiaKawalAPI ? =null)
       : Serializable