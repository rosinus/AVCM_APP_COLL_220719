package com.vigeo.avcm.collect.view.viewmodel

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CollectService {
    @FormUrlEncoded
    @POST("appApi/collectionApp/collectionPlace.do")
    fun getGpsList(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ=="
    ): Call<CollectModel>
}
