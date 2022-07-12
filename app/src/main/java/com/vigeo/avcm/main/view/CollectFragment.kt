package com.vigeo.avcm.main.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.databinding.FragmentCollectBinding
import com.vigeo.avcm.main.viewmodel.KakaoMapMarker
import net.daum.mf.map.api.MapView

class CollectFragment : Fragment() {
    private var mbinding : FragmentCollectBinding? = null
    private val binding : FragmentCollectBinding get() = mbinding!!

    var mapViewContainer: ViewGroup? = null
    var mapView: MapView? = null
    private lateinit var kakaoMapMarker : KakaoMapMarker



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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "CollectFragment - onCreate() called")
        mbinding = FragmentCollectBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kakaoMapMarker = (activity as MainActivity).kakaoMapMarker

        mapView = kakaoMapMarker.mapView
        mapViewContainer = binding.mapView
        mapViewContainer?.addView(kakaoMapMarker.mapView,0)

        kakaoMapMarker.btn_marker()
        kakaoMapMarker.btn_marker2()
    }

    override fun onStop() {
        super.onStop()
        mapViewContainer?.removeView(mapView)
    }

}