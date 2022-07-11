package com.vigeo.avcm.viewmodel

import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.daum.mf.map.api.MapView

@Suppress("UNCHECKED_CAST")
class KakaoMapMarkerProviderFactory(
    val mapView : MapView,
    val lm: LocationManager
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if ( modelClass.isAssignableFrom(KakaoMapMarker::class.java)){
            return KakaoMapMarker(mapView,lm) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}