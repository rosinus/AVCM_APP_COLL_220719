package com.vigeo.avcm.purchase.repository

import com.vigeo.avcm.purchase.data.model.CollectionList
import com.vigeo.avcm.purchase.data.model.Service.RetrofitInstance.collectApi
import retrofit2.Response

class CollectRepositoryImpl : CollectRepository {
    override suspend fun collectList(
        vigeoToken : String ,
        userNo : String
    ) : Response<CollectionList> {
        return collectApi.collectList(vigeoToken,userNo)
    }

    override suspend fun collectDetail(
        vigeoToken : String,
        userNo : String
    ) : Response<CollectionList> {
        return collectApi.collectDetail(vigeoToken,userNo)
    }
}