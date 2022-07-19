package com.vigeo.avcm.collect.view.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentCollect1Binding
import com.vigeo.avcm.databinding.FragmentCollect2Binding

class CollectSelectFragment2 : Fragment() {
    private var _binding : FragmentCollect2Binding? = null
    private val binding : FragmentCollect2Binding get() = _binding!!
    var count = 0
    var count1 : Double = 0.00
    //호출 했을때

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //Activity에 의존 프레그먼트를 안고 있는 액티비에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCollect2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Fragment1에서 보낸 값 받아야함
        var mylocation = arguments?.getString("mylocation")
        Log.d("3중필름에서 받는 ?","위치는?"+ mylocation)

        if(mylocation == null){
            mylocation = arguments?.getString("backmylocation")
            Log.d("되돌아와서 멀칭 필름에서 받는 ?","위치는?"+ mylocation)
        }
        var previousCount = arguments?.getString("count")
        Log.d("3중에서 받는 필름 :?","갯수는"+previousCount)

        if(previousCount == null){
            previousCount = arguments?.getString("backcount")
            Log.d("되돌아 와서"," 멀칭필름에서 이정도받음"+previousCount)
        }
        var lat = arguments?.getDouble("lat")
        Log.d("3중에서 받는 lat","이정도받음"+lat)
        if(lat == 0.00 || lat == 0.0){
            lat = arguments?.getDouble("backlat")
            Log.d("되돌아 와서"," 멀칭필름에서 lat 이정도받음"+lat)
        }
        var lot = arguments?.getDouble("lot")
        Log.d("3중에서 받는 lot","이정도받음"+lot)
        if(lot == 0.00 || lot == 0.0){
            lot = arguments?.getDouble("backlot")
            Log.d("되돌아 와서"," 멀칭필름에서 lot 이정도받음"+lot)
        }
        var userNo = arguments?.getString("userNo")
        if(userNo == null){
            userNo = arguments?.getString("backUserNo")
            Log.d("되돌아 와서"," 받는 유저No"+userNo)
        }
        binding.btnMinus.setOnClickListener {
            if(count < 1){
                count = 0
            }else {
                count --
                binding.filmStep12.setText(count.toString())
            }
        }

        binding.btnPlus.setOnClickListener {
            count ++
            binding.filmStep12.setText(count.toString())
        }
        var count1 = count.toString()
        binding.btnNext.setOnClickListener {
            count1 = binding.filmStep12.text.toString()
            var fragmentCollect3 :CollectSelectFragment3 = CollectSelectFragment3()
            var bundle = Bundle()
            bundle.putString("mylocation", mylocation.toString())
            bundle.putString("count3", previousCount.toString())
            bundle.putString("count12", count1)
            bundle.putDouble("lat", lat!!)
            bundle.putDouble("lot", lot!!)
            bundle.putString("userNo",userNo)

            fragmentCollect3.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect3).commit()
        }

        binding.btnBack.setOnClickListener {
            var fragmentBack : CollectSelectFragment = CollectSelectFragment()
            var bundle = Bundle()
            bundle.putString("backmylocation", mylocation.toString())
            bundle.putString("backUserNo", userNo)
            bundle.putDouble("backlat", lat!!.toDouble())
            Log.d("12중필름에서 뒤로 보내는 : ?","lat 값은?"+lat)
            bundle.putDouble("backlot", lot!!.toDouble())
            Log.d("12중필름에서 뒤로 보내는 : ?","lot 값은?"+lot)
            fragmentBack.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentBack).commit()
        }
    }
}