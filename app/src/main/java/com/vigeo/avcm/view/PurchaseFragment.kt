package com.vigeo.avcm.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.R

class PurchaseFragment : Fragment() {

    companion object{
        //log 출력을 편하게 하기 위해서
        const val TAG : String = "로그"

        fun newInstance() : PurchaseFragment {
            return PurchaseFragment()
        }
    }
    //호출 했을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "PurchaseFragment - onCreate() called")
    }
    //Activity에 의존 프레그먼트를 안고 있는 액티비에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "PurchaseFragment - onCreate() called")
    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "PurchaseFragment - onCreate() called")

        var view = inflater.inflate(R.layout.fragment_purchase, container, false)

        return view
    }
}