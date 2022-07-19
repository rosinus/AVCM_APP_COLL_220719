package com.vigeo.avcm.purchase.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collect(
    @field:Json(name = "applicantNo")
    var applicantNo: Int?,
    @field:Json(name = "collectAddr")
    var collectAddr: String?,
    @field:Json(name = "collectDate")
    var collectDate: String?,
    @field:Json(name = "collectGb")
    var collectGb: String?,
    @field:Json(name = "collectGbNm")
    var collectGbNm: String?,
    @field:Json(name = "collectGpsLen")
    var collectGpsLen: Double?,
    @field:Json(name = "collectGpsLon")
    var collectGpsLon: Double?,
    @field:Json(name = "collectInfoProdLength1")
    var collectInfoProdLength1: Double?,
    @field:Json(name = "collectInfoProdLength2")
    var collectInfoProdLength2: Double?,
    @field:Json(name = "collectInfoProdLength3")
    var collectInfoProdLength3: Double?,
    @field:Json(name = "collectInfoProdWeight1")
    var collectInfoProdWeight1: Double?,
    @field:Json(name = "collectInfoProdWeight2")
    var collectInfoProdWeight2: Double?,
    @field:Json(name = "collectInfoProdWeight3")
    var collectInfoProdWeight3: Double?,
    @field:Json(name = "collectNo")
    var collectNo: Int?,
    @field:Json(name = "prodGb1")
    var prodGb1: String?,
    @field:Json(name = "prodGb2")
    var prodGb2: String?,
    @field:Json(name = "prodGb3")
    var prodGb3: String?,
    @field:Json(name = "prodGbNm1")
    var prodGbNm1: String?,
    @field:Json(name = "prodGbNm2")
    var prodGbNm2: String?,
    @field:Json(name = "prodGbNm3")
    var prodGbNm3: String?,
    @field:Json(name = "prodInfoLength1")
    var prodInfoLength1: Double?,
    @field:Json(name = "prodInfoLength2")
    var prodInfoLength2: Double?,
    @field:Json(name = "prodInfoLength3")
    var prodInfoLength3: Double?,
    @field:Json(name = "prodInfoWeight1")
    var prodInfoWeight1: Double?,
    @field:Json(name = "prodInfoWeight2")
    var prodInfoWeight2: Double?,
    @field:Json(name = "prodInfoWeight3")
    var prodInfoWeight3: Double?,
    @field:Json(name = "prodLength")
    var prodLength: Double?,
    @field:Json(name = "prodWeight")
    var prodWeight: Double?,
    @field:Json(name = "r")
    var r: Double?
)