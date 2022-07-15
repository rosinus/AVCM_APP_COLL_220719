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
        Log.d("3중필름m선택 후 다음 누를시 :","주소 전송"+mylocation)
        var previousCount = arguments?.getString("count")
        Log.d("3중필름m선택 후 다음 누를시 :","3중필름의 수거m는"+previousCount)



        binding.btnMinus.setOnClickListener {
            Log.d("클릭","마이너스 클릭")
            if(count < 1){
                count = 0
            }else {
                count --
                binding.filmStep12.setText(count.toString())
            }
        }

        binding.btnPlus.setOnClickListener {
            Log.d("클릭","플러스 클릭")
            count ++
            binding.filmStep12.setText(count.toString())
        }

        binding.btnNext.setOnClickListener {
            var fragmentCollect3 :CollectSelectFragment3 = CollectSelectFragment3()
            var bundle = Bundle()
            bundle.putString("mylocation", mylocation.toString())
            bundle.putString("count3", previousCount.toString())
            bundle.putString("count12", count.toString())
            Log.d("마지막으로 가기전","주소는?" +mylocation)
            Log.d("3중필름","갯수는?" +previousCount)
            Log.d("1.2중필름","갯수는?" +count.toString())
            fragmentCollect3.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect3).commit()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, CollectSelectFragment()).commit()
        }
    }
}