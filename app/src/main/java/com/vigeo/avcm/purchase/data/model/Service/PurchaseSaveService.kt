package com.vigeo.avcm.purchase.data.model.Service

import com.vigeo.avcm.purchase.data.model.SaveVo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PurchaseSaveService {
    @FormUrlEncoded
    @POST("appApi/purchaseApp/purchaseHistoryInsert.do")
    fun purchaseSave(
        @Field("vigeoToken") vigeoToken: String = "O304UIUw3P78ZZPC5qBkmQ==",
        @Field("prodNo") prodNo: String,
        @Field("consumerNo") consumerNo: String,
        @Field("prodCmpnyNo") prodCmpnyNo: String,
        @Field("buyCnt") buyCnt: String
    ): Call<SaveVo>
}