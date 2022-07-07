package com.vigeo.avcm.data.repository.retrofit

import com.google.gson.JsonObject
import com.vigeo.avcm.data.model.User
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RetrofitService {
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
}