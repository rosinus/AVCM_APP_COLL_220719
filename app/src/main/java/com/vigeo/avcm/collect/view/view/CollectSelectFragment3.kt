package com.vigeo.avcm.collect.view.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentCollect1Binding
import com.vigeo.avcm.databinding.FragmentCollect2Binding
import com.vigeo.avcm.databinding.FragmentCollect3Binding

class CollectSelectFragment3 : Fragment() {
    private var _binding : FragmentCollect3Binding? = null
    private val binding : FragmentCollect3Binding get() = _binding!!

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

        _binding = FragmentCollect3Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, CollectSelectFragment2()).commit()
        }
    }
}