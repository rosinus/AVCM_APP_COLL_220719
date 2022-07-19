package com.vigeo.avcm.purchase.data.model.Service

import com.vigeo.avcm.purchase.data.model.PurchaseList
import retrofit2.Response
import retrofit2.http.*

interface PurchaseService {
    @POST("appApi/purchaseApp/purchaseList.do")
    suspend fun purchaseList(
        @Query("vigeoToken") vigeoToken : String ,
        @Query("prodNm")     prodNm : String ,
        @Query("prodGb")     prodGb : String ,
        @Query("firstIndex") firstIndex : Int ,
        @Query("lastIndex")  lastIndex  : Int
    ) : Response<PurchaseList>

    @POST("appApi/purchaseApp/purchaseList.do")
    suspend fun purchaseDetail(
        @Query("vigeoToken") vigeoToken : String,
        @Query("prodNo")     prodNo : String,
        @Query("firstIndex") firstIndex : Int ,
        @Query("lastIndex")  lastIndex  : Int
    ) : Response<PurchaseList>
}