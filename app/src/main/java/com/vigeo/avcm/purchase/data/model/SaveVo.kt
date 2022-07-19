package com.vigeo.avcm.purchase.data.model


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vigeo.avcm.login.model.UserObject

data class SaveVo(
    @SerializedName("message")
    val message : Boolean
)