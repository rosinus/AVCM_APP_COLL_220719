package com.vigeo.avcm.collect.view.viewmodel

import android.text.Editable
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CollectService {
    @FormUrlEncoded
    @POST("appApi/collectionApp/collectionPlace.do")
    fun getGpsList(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                ): Call<CollectModel>


    @FormUrlEncoded
    @POST("appApi/collectionApp/collectionSignup.do")
    fun collectionSignup(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("prodInfoLength1") prodInfoLength1: Double?,
        @Field("prodInfoLength2") prodInfoLength2: Double?,
        @Field("prodInfoLength3") prodInfoLength3: Double,
        @Field("applicantNo") applicantNo: Int,
        @Field("collectAddr") collectAddr: String?,
        @Field("collectGpsLen") collectGpsLen: Double,
        @Field("collectGpsLon") collectGpsLon: Double
    ): Call<CollectModel>
}
