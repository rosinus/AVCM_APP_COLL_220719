package com.vigeo.avcm.join.model

import com.google.gson.annotations.SerializedName

data class JoinVO(
    @SerializedName("userChack")
    val isUser : Boolean,

    @SerializedName("message")
    val result : String
)
