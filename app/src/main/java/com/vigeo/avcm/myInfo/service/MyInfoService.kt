package com.vigeo.avcm.myInfo.service

import com.google.gson.JsonObject
import com.vigeo.avcm.data.model.User
import com.vigeo.avcm.myInfo.viewModel.MyInfoVO
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MyInfoService {

    //POST 예제
    @FormUrlEncoded
    @POST("appApi/userApp/faqList.do")
    fun faqList(@Field("vigeoToken") vigeoToken : String = "O304UIUw3P78ZZPC5qBkmQ==",
                @Field("userGb") userGb : String = "04"
    ): Call<MyInfoVO>

}