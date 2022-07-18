package com.vigeo.avcm.login.model

import com.google.gson.annotations.SerializedName

data class LoginVO(

    @SerializedName("userChack")
    val isUser : Boolean,

    @SerializedName("message")
    val result : String,


    @SerializedName("error")
    val errorMsg : String,


    @SerializedName("userInfo")
    val userInfo : UserObject?


)

data class UserObject(
    val userNo :  String?,
    val userGb : String?,
    val userGbNm : String?,
    val stateCdNm : String?,
    val userId : String?,
    val userPw : String?,
    val userNm : String?,
    val cmpnyNm : String?,
    val phoneNum : String?,
    val stateCd : String?,
    val zipCd : String?,
    val addr : String?,
    val addrDetail: String?,
    val fcmToken : String?
)
