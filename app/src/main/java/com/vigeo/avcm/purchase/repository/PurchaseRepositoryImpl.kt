package com.vigeo.avcm.purchase.repository

import com.vigeo.avcm.purchase.data.model.PurchaseList
import com.vigeo.avcm.purchase.data.model.Service.RetrofitInstance.api
import retrofit2.Response

class PurchaseRepositoryImpl : PurchaseRepository {
    override suspend fun purchaseList(
        vigeoToken : String ,
        prodNm : String ,
        prodGb : String ,
        firstIndex : Int ,
        lastIndex  : Int
    ) : Response<PurchaseList> {
        return api.purchaseList(vigeoToken,prodNm,prodGb,firstIndex,lastIndex)
    }

    override suspend fun purchaseDetail(
        vigeoToken : String,
        prodNo : String,
        firstIndex : Int,
        lastIndex  : Int
    ) : Response<PurchaseList> {
        return api.purchaseDetail(vigeoToken,prodNo,0,1)
    }
}