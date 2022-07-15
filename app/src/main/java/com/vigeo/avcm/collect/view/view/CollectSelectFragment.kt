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
        // 지금 생각 해보니 Frament12를 거치고 3에서 한번에 받아서 처리해도 될거 같음.. 바보코딩임
        val mylocation = arguments?.getString("number1")
        Log.d("지도에서 다음 누를시 :","주소 전송"+mylocation)
        var count1 = count.toString()

        binding.btnMinus.setOnClickListener {
            Log.d("클릭","마이너스 클릭")
            if(count < 1){
                count = 0
            }else {
                count --
                binding.filmStep3.setText(count.toString())
            }
        }


        binding.btnPlus.setOnClickListener {
            Log.d("클릭","플러스 클릭")
            count ++
            binding.filmStep3.setText(count.toString())
        }

        //여기부턴 NumActivity 랑 똑같습니당 ^^

        binding.btnNext.setOnClickListener {
            var fragmentCollect2 :CollectSelectFragment2 = CollectSelectFragment2()
            var bundle = Bundle()
            bundle.putString("mylocation", mylocation.toString())
            bundle.putString("count", count.toString())
            fragmentCollect2.arguments = bundle

            Log.d("넘길 3중필름 m은","몇개?" + count+"미터")
            Log.d("1,2중 외피 가기전에 주소","잘 오나요??" + mylocation)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect2).commit()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(getActivity(), CollectActivity::class.java)
            startActivity(intent)
        }
    }
}