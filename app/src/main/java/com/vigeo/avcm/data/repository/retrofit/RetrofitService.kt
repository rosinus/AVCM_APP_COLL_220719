package com.vigeo.avcm.data.repository.retrofit

import com.google.gson.JsonObject
import com.vigeo.avcm.data.model.User
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    val vigeoToken: String //비제오토큰
        get() = "O304UIUw3P78ZZPC5qBkmQ=="

    val userGb: String //사용자 구분값, 04=농민
        get() = "04"


    //GET 예제
    @GET("jbcpvigeo/test.do")
    fun getUser(): Call<User>

    @GET("jbcpvigeo/{page}")
    fun getUserPage(@Path("page") page: String): Call<User>


//    @GET("posts/1")
//    fun getStudent(@Query("school_id") schoolId: Int,
//                   @Query("grade") grade: Int,
//                   @Query("classroom") classroom: Int): Call<ExampleResponse>
//
//
//    //POST 예제
    @FormUrlEncoded
    @POST("posts")
    fun getContactsObject(@Field("idx") idx: String): Call<JsonObject>

    //사용자 존재 여부 확인
    @FormUrlEncoded
    @POST("appApi/userApp/isUserExist.do")
    fun isUserExist(@Field("vigeoToken") vigeoToken : String = this.vigeoToken,
                    @Field("userGb") userGb : String = this.userGb,
                    @Field("userId") userId : String,
                    @Field("userPw") userPw : String
    ): Call<Boolean>

}