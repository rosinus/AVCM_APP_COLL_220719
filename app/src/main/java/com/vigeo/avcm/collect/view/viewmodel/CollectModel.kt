package com.vigeo.avcm.collect.view.viewmodel

import com.google.gson.annotations.SerializedName
import com.vigeo.avcm.myInfo.viewModel.faqListObject

data class CollectModel(

    @SerializedName("collectList")
    val collectList: ArrayList<collectListObject>,

    @SerializedName("collectInsert")
    var collectInsert: String
)


data class collectListObject(
    @SerializedName("collectGroundNo")
    var collectGroundNo : Int,
    @SerializedName("collectGroundNm")
    var collectGroundNm : String,
    @SerializedName("collectGroundAddr")
    var collectGroundAddr : String,
    @SerializedName("collectGroundUseYn")
    var collectGroundUseYn : String,
    @SerializedName("gpsLen")
    var gpsLen : Double,
    @SerializedName("gpsLon")
    var gpsLon : Double,
)