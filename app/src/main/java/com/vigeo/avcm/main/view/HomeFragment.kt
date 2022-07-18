package com.vigeo.avcm.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.R
import com.vigeo.avcm.collect.view.view.CollectActivity
import com.vigeo.avcm.collect.view.view.CollectSelectFragment
import com.vigeo.avcm.databinding.FragmentHomeBinding
import com.vigeo.avcm.myInfo.view.FaqActivity
import com.vigeo.avcm.myInfo.view.GuideVideoActivity

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!

    companion object{
        //log 출력을 편하게 하기 위해서
        const val TAG : String = "로그"

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }
    //호출 했을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment - onCreate() called")
    }
    //Activity에 의존 프레그먼트를 안고 있는 액티비에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment - onCreate() called")
    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.butCollect.setOnClickListener {
        val intent = Intent(getActivity(), CollectActivity::class.java)
        startActivity(intent)
        }
        binding.butPurchase.setOnClickListener {
            var fragmentNext : PurchaseFragment = PurchaseFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_frame,fragmentNext).commit()
        }
        binding.butFaq.setOnClickListener {
            val intent = Intent(getActivity(), FaqActivity::class.java)
            startActivity(intent)
        }
        binding.butGuide.setOnClickListener {
            val intent = Intent(getActivity(), GuideVideoActivity::class.java)
            startActivity(intent)
        }
    }
}