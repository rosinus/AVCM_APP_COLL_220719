package com.vigeo.avcm.myInfo.service

import com.google.gson.JsonObject
import com.vigeo.avcm.data.model.User
import com.vigeo.avcm.myInfo.viewModel.MyInfoVO
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MyInfoService {

    //faqList 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/faqList.do")
    fun faqList(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                @Field("userGb") userGb : String = "04"
    ): Call<MyInfoVO>

    //faqList 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isUserUpdate.do")
    fun isUserUpdate(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userNo") userNo: String = "",
        @Field("userGb") userGb: String = "04",
        @Field("userId") userId: String = "",
        @Field("userPw") userPw: String = "",
        @Field("userNm") userNm: String = "",
        @Field("zipCd") zipCd: String = "",
        @Field("addr") addr: String = "",
        @Field("addrDetail") addrDetail: String = ""
    ): Call<MyInfoVO>

    //faqList 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isUserExist.do")
    fun isUserExist(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userGb") userGb: String = "04",
        @Field("phoneNum") userId: String = "",
    ): Call<MyInfoVO>

    //faqList 불러오기
    @FormUrlEncoded
    @POST("appApi/userApp/isUserDelect.do")
    fun isUserDelect(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("userNo") userNo: String = "",
    ): Call<MyInfoVO>




}