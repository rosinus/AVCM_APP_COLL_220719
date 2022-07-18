package com.vigeo.avcm.purchase.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseList(
    @field:Json(name = "message")
    var message: String?,
    @field:Json(name = "purchaseCnt")
    var purchaseCnt: Int?,
    @field:Json(name = "purchaseList")
    var purchaseList: List<Purchase>
)