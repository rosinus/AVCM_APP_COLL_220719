package com.vigeo.avcm.data.repository.retrofit

import com.vigeo.avcm.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("com/user/userGrid.do")
    fun ApiTest(): Call<User>

}