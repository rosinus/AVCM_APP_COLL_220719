package com.vigeo.avcm.login.service

import com.vigeo.avcm.login.model.LoginVO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    //로그인 시 존재하는 계정인지 확인
    @FormUrlEncoded
    @POST("appApi/userApp/isUserExist.do")
    fun isUserExist(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                    @Field("userGb") userGb : String?,
                    @Field("phoneNum") userId : String?, //api에서 userId를 phoneNum으로 받고있음
                    @Field("userPw") userPw : String?
    ): Call<LoginVO>


    //비밀번호 초기화
    @FormUrlEncoded
    @POST("appApi/userApp/isUserPwUpdate.do")
    fun isUserPwUpdate(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                       @Field("userGb") userGb : String = "04",
                       @Field("phoneNum") userId : String?, //api에서 userId를 phoneNum으로 받고있음
                       @Field("userNm") userNm : String?
    ): Call<LoginVO>


    //로그인
    @FormUrlEncoded
    @POST("appApi/userApp/isUserLogin.do")
    fun isUserLogin(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
        //@Field("userGb") userGb : String = "04", //아이디 중복은 구분 값 필요없음 전체에서 조회
                    @Field("phoneNum") userId : String?, //api에서 userId를 phoneNum으로 받고있음
                    @Field("userPw") userPw : String?
    ): Call<LoginVO>
}