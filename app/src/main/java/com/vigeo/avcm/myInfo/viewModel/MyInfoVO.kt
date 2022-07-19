package com.vigeo.avcm.myInfo.viewModel

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class MyInfoVO(

    @SerializedName("faqList")
    val faqList: ArrayList<faqListObject>,

    @SerializedName("message")
    val message: String,

    @SerializedName("error")
    val error: String,

    @SerializedName("userChack")
    val userChack: String,

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑
)

data class faqListObject(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

)
