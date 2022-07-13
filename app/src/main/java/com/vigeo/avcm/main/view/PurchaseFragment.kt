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
import com.vigeo.avcm.databinding.FragmentPurchaseBinding
import com.vigeo.avcm.purchase.view.PurchaseActivity

class PurchaseFragment : Fragment() {

    private var _binding : FragmentPurchaseBinding? = null
    private val binding : FragmentPurchaseBinding get() = _binding!!

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

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPurchaseNavItemListener()
        if (savedInstanceState == null) {
            binding.purchaseNavi.selectedItemId = R.id.all
        }
        binding.testBtn.setOnClickListener(){
            Log.d("클릭이벤트확인", "클릭됨")
            val intent = Intent(activity, PurchaseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onPurchaseNavItemListener() {
        binding.purchaseNavi.setOnItemSelectedListener {
            //Log.d("클릭")
            when (it.itemId) {
                R.id.all -> {
                    Log.d(TAG, "all - 전체 버튼 클릭")
                    true
                }
                R.id.outer -> {
                    Log.d(TAG, "outer - 외피 필름 클릭")
                    true
                }
                R.id.endothelial -> {
                    Log.d(TAG, "endothelial - 내피 필름 클릭")
                    true
                }
                R.id.mulching -> {
                    Log.d(TAG, "mulching - 멀칭 필름 클릭")
                    true
                }
                else -> false
            }

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}