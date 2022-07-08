package com.vigeo.avcm.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.databinding.FragmentCollectBinding
import net.daum.mf.map.api.MapView

class CollectFragment : Fragment() {
    var mapViewContainer: ViewGroup? = null
    var mapView: MapView? = null

    companion object{
        //log 출력을 편하게 하기 위해서
        const val TAG : String = "로그"

        fun newInstance() : CollectFragment {
            return CollectFragment()
        }
    }


    //호출 했을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "CollectFragment - onCreate() called")
    }

    //Activity에 의존 프레그먼트를 안고 있는 액티비에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "CollectFragment - onCreate() called")
    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        Log.d(TAG, "CollectFragment - onCreate() called")
        val binding = FragmentCollectBinding.inflate(inflater, container, false)
        mapView = MapView(activity)
        mapViewContainer = binding.mapView
        mapViewContainer?.addView(mapView,0)


/*        //마커 부분
        val mapPoint = MapPoint.mapPointWithGeoCoord(35.817826091485685, 127.10793054559015)

        //지도 클릭시 처음에 어디를 띄워줄건지 설정, 확대 레벨 설정 (값이 작을수록 더 확대됨)
        mapView!!.setMapCenterPoint(mapPoint, true)
        mapView!!.setZoomLevel(1, true)

        //마커 생성
        val marker = MapPOIItem()
        marker.itemName = "전주 강우영집하장"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        mapView!!.addPOIItem(marker)*/
        return binding.root
    }


    override fun onStop() {
        super.onStop()
        mapViewContainer?.removeView(mapView)
    }

}