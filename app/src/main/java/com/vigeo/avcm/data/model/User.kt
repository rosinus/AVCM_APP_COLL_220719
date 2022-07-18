package com.vigeo.avcm.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("message")
    val message: String,

    @SerializedName("vigeoCode")
    val vigeoCode: String,

    //받을 데이터
    var userNo : String = "",
    var userNm : String = "",
    var userId : String = "",
    var email : String = "",
    var phoneNum : String = "",
    var zipCd : String = "",
    var addr : String = "",
    var addrDetail : String = "",
    var phoneCd : String = ""

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑
)