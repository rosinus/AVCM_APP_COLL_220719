package com.vigeo.avcm.main.viewmodel

import androidx.lifecycle.ViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class KakaoMapMarker (
    val mapView : MapView
) : ViewModel() {

    fun btn_marker(){

        //마커 부분
        val mapPoint = MapPoint.mapPointWithGeoCoord(35.817826091485685, 127.10793054559015)

        //마커 생성
        val marker = MapPOIItem()
        marker.itemName = "전주 강우영집하장"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        mapView.addPOIItem(marker)
    }

    fun btn_marker2(){
        //마커 부분
        val mapPoint = MapPoint.mapPointWithGeoCoord(35.837826091485685, 127.13793054559015)

        //마커 생성
        val marker = MapPOIItem()
        marker.itemName = "전주 강우영집하장"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        mapView.addPOIItem(marker)
    }

}