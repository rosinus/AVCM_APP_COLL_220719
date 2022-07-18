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
        if(mylocation == null){
            mylocation = arguments?.getString("backmylocation")
        }
        var previousCount = arguments?.getString("count")
        if(previousCount == null){
            previousCount = arguments?.getString("backcount")
        }
        var lat = arguments?.getDouble("lat")
        var lot = arguments?.getDouble("lot")
        var userNo = arguments?.getInt("userNo")
        if(userNo == null){
            userNo = arguments?.getInt("backUserNo")
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

        binding.btnNext.setOnClickListener {
            var fragmentCollect3 :CollectSelectFragment3 = CollectSelectFragment3()
            var bundle = Bundle()
            bundle.putString("mylocation", mylocation.toString())
            bundle.putString("count3", previousCount.toString())
            bundle.putString("count12", count.toString())
            bundle.putDouble("lat", lat!!)
            bundle.putDouble("lot", lot!!)
            bundle.putInt("userNo",userNo!!)
            Log.d("잘감","위도"+userNo)

            fragmentCollect3.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect3).commit()
        }

        binding.btnBack.setOnClickListener {
            var fragmentBack : CollectSelectFragment = CollectSelectFragment()
            var bundle = Bundle()
            bundle.putString("backmylocation", mylocation.toString())
            bundle.putInt("backUserNo", userNo!!)
            fragmentBack.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentBack).commit()
        }
    }
}