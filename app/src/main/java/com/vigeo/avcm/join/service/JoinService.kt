package com.vigeo.avcm.join.service

import com.vigeo.avcm.join.model.JoinVO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface JoinService {

    //회원가입 가입 처리 시 사용자 추가
    //@Field는 파라미터 순서 변경에도 Field내 명칭만 맞다면 매핑해주는 역할
    //값이 있는 파라미터(ex.vigeoToken, userGb)는 메소드 호출 시 파라미터 부재일 때 대체하는 기본값
    @FormUrlEncoded
    @POST("appApi/userApp/userInsert.do")
    fun userInsert(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                   @Field("userGb") userGb : String = "04",
                   @Field("userId") userId : String?,
                   @Field("userPw") userPw : String?,
                   @Field("userNm") userNm : String?,
                   @Field("zipCd") zipCd : String,
                   @Field("addr") addr : String,
                   @Field("addrDetail") addrDetail : String
    ): Call<JoinVO>


    //로그인 시 존재하는 계정인지 확인
    //회원가입 시 이미 존재하는 계정인지 확인
    @FormUrlEncoded
    @POST("appApi/userApp/isUserExist.do")
    fun isUserExist(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                    @Field("userGb") userGb : String?,
                    @Field("phoneNum") userId : String?, //api에서 userId를 phoneNum으로 받고있음
                    @Field("userPw") userPw : String?
    ): Call<JoinVO>

}