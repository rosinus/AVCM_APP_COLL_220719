package com.vigeo.avcm.purchase.repository

import com.vigeo.avcm.purchase.data.model.CollectionList
import retrofit2.Response

interface CollectRepository {
    suspend fun collectList(
        vigeoToken : String ,
        userNo : String ,

    ) : Response<CollectionList>

    suspend fun collectDetail(
        vigeoToken : String,
        userNo : String,
    ) : Response<CollectionList>
}