package com.vigeo.avcm.purchase.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Purchase(
    @field:Json(name = "cmpnyBizrno")
    var cmpnyBizrno: String?,
    @field:Json(name = "cmpnyNm")
    var cmpnyNm: String?,
    @field:Json(name = "manufacturer")
    var manufacturer: String?,
    @field:Json(name = "phoneNum")
    var phoneNum: String?,
    @field:Json(name = "prodCmpUserNo")
    var prodCmpUserNo: Int?,
    @field:Json(name = "prodGb")
    var prodGb: String?,
    @field:Json(name = "prodGbNm")
    var prodGbNm: String?,
    @field:Json(name = "prodImg")
    var prodImg: String?,
    @field:Json(name = "prodLength")
    var prodLength: Int?,
    @field:Json(name = "prodLtdCnt")
    var prodLtdCnt: Int?,
    @field:Json(name = "prodLtdYn")
    var prodLtdYn: String?,
    @field:Json(name = "prodNm")
    var prodNm: String?,
    @field:Json(name = "prodNo")
    var prodNo: Int?,
    @field:Json(name = "prodPrice")
    var prodPrice: Int?,
    @field:Json(name = "prodThickness")
    var prodThickness: Int?,
    @field:Json(name = "prodWidth")
    var prodWidth: Int?,
    @field:Json(name = "r")
    var r: Double?,
    @field:Json(name = "useDateFr")
    var useDateFr: String?,
    @field:Json(name = "useDateTo")
    var useDateTo: String?,
    @field:Json(name = "userNm")
    var userNm: String?
)