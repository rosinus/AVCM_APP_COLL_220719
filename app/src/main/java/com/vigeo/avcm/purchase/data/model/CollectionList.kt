package com.vigeo.avcm.purchase.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionList(
    @field:Json(name = "collectList")
    var collectList: List<Collect?>?,
    @field:Json(name = "collectListCnt")
    var collectListCnt: Int?,
    @field:Json(name = "message")
    var message: String?
)