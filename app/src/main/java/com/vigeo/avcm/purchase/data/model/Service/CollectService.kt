package com.vigeo.avcm.purchase.data.model.Service

import com.vigeo.avcm.purchase.data.model.CollectionList
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface CollectService {
    @POST("appApi/collectionApp/collectionHistoryList.do")
    suspend fun collectList(
        @Query("vigeoToken") vigeoToken : String,
        @Query("userNo")     userNo : String
    ) : Response<CollectionList>

    @POST("appApi/purchaseApp/purchaseList.do")
    suspend fun collectDetail(
        @Query("vigeoToken") vigeoToken : String,
        @Query("userNo")     userNo : String
    ) : Response<CollectionList>
}