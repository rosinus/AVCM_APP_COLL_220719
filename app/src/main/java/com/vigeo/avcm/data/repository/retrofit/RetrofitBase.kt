package com.vigeo.avcm.data.repository.retrofit

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBase {

    private val BASE_URL = "http://192.168.0.193:8080/"

    fun retrofitBase() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    fun serviceRetrofit() : RetrofitService{
        return retrofitBase().create(RetrofitService::class.java);
    }

}