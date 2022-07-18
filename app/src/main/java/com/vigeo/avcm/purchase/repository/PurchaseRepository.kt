package com.vigeo.avcm.purchase.repository

import com.vigeo.avcm.purchase.data.model.PurchaseList
import retrofit2.Response
import retrofit2.http.Query

interface PurchaseRepository {
    suspend fun purchaseList(
        vigeoToken : String ,
        prodNm : String ,
        prodGb : String ,
        firstIndex : Int ,
        lastIndex  : Int
    ) : Response<PurchaseList>

    suspend fun purchaseDetail(
        vigeoToken : String,
        prodNo : String,
        firstIndex : Int,
        lastIndex  : Int
    ) : Response<PurchaseList>
}