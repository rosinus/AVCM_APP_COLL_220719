package com.vigeo.avcm.collect.view.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentCollect1Binding
import java.text.DecimalFormat

class CollectSelectFragment : Fragment() {
    private var _binding : FragmentCollect1Binding? = null
    private val binding : FragmentCollect1Binding get() = _binding!!

    //호출 했을때
    var count = 0
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
        _binding = FragmentCollect1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NumActivity 에서 넘겨온 데이터를 받는 작업
        var mylocation = arguments?.getString("number1")
        if(mylocation == null){
            mylocation = arguments?.getString("backmylocation")
        }
        var lat = arguments?.getDouble("lat")
        var lot = arguments?.getDouble("lot")
        var userNo = arguments?.getInt("userNo")
        if(userNo == null){
            userNo = arguments?.getInt("backUserNo")
        }
        var count1 = count.toString()

        binding.btnMinus.setOnClickListener {
            if(count < 1){
                count = 0
            }else {
                count --
                binding.filmStep3.setText(count.toString())
            }
        }


        binding.btnPlus.setOnClickListener {
            count ++
            binding.filmStep3.setText(count.toString())
        }

        //여기부턴 NumActivity 랑 똑같습니당 ^^

        binding.btnNext.setOnClickListener {
            var fragmentCollect2 :CollectSelectFragment2 = CollectSelectFragment2()
            var bundle = Bundle()
            bundle.putString("mylocation", mylocation.toString())
            bundle.putString("count", count.toString())
            bundle.putDouble("lat", lat!!)
            bundle.putDouble("lot", lot!!)
            bundle.putInt("userNo",userNo!!)
            fragmentCollect2.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect2).commit()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(getActivity(), CollectActivity::class.java)
            startActivity(intent)
        }
    }
}