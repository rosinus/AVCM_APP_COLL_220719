package com.vigeo.avcm.myInfo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.databinding.FragmentMyInfoBinding

class MyInfoFragment : Fragment() {

    private lateinit var myInfoBinding: FragmentMyInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyInfo : ", "MyInfo - onCreate() called")

    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("MyInfo : ", "MyInfo - onCreateView() called")
        myInfoBinding = FragmentMyInfoBinding.inflate(inflater, container, false)

        myInfoBinding.myInfoFrequentlyAskedQuestions.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            val intent = Intent(activity, FaqActivity::class.java)
            startActivity(intent)
        }

        return myInfoBinding.root
    }
}

