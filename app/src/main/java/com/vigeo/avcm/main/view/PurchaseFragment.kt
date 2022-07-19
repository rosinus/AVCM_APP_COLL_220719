package com.vigeo.avcm.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentPurchaseBinding
import com.vigeo.avcm.purchase.adapter.PurchaseAdapter
import com.vigeo.avcm.purchase.view.CollectActivity
import com.vigeo.avcm.purchase.view.PurchaseActivity
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel

class PurchaseFragment : Fragment() {

    private var _binding : FragmentPurchaseBinding? = null
    private val binding : FragmentPurchaseBinding get() = _binding!!

    private lateinit var purchaseViewModel: PurchaseViewModel
    private lateinit var purchaseAdapter: PurchaseAdapter

    var prodGb : String = ""
    var lastIndex : Int = 1

    companion object{
        //log 출력을 편하게 하기 위해서
        const val TAG : String = "로그"

//        fun newInstance() : PurchaseFragment {
//            return PurchaseFragment()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "PurchaseFragment - onCreate() called")

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        onPurchaseNavItemListener()

        setupRecyclerView()

        purchaseViewModel = (activity as MainActivity).purchaseViewModel

        purchaseViewModel.result.observe(viewLifecycleOwner){ response ->
            val purchase = response.purchaseList
            purchaseAdapter.submitList(purchase)
            Log.d("데이터",purchase.toString())
        }
        purchaseLists()
        binding.searchBtn.setOnClickListener(){
            purchaseLists()
        }

        scorll()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onPurchaseNavItemListener() {
        binding.purchaseNavi.setOnItemSelectedListener {
            //Log.d("클릭")
            when (it.itemId) {
                R.id.all -> {
                    prodGb = ""
                    purchaseLists()
                    Log.d(TAG, "all -s 전체 버튼 클릭")
                    true
                }
                R.id.outer -> {
                    prodGb = "01"
                    purchaseLists()
                    Log.d(TAG, "outer - 외피 필름 클릭")
                    true
                }
                R.id.endothelial -> {
                    prodGb = "02"
                    purchaseLists()
                    Log.d(TAG, "endothelial - 내피 필름 클릭")
                    true
                }
                R.id.mulching -> {
                    prodGb = "03"
                    purchaseLists()
                    Log.d(TAG, "mulching - 멀칭 필름 클릭")
                    val intent = Intent(activity, CollectActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }

        }

    }

    private fun setupRecyclerView() {
        purchaseAdapter = PurchaseAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = purchaseAdapter
        }
    }

    private fun purchaseLists() {
        Log.d("리스트","진입")
        var prodNm = binding.etSearch.text.toString()
        purchaseViewModel.purchaseLists(prodGb.toString(),prodNm,0,lastIndex)
        //binding.etSearch.addTextChangedListener { text: Editable? ->
    }

    private fun scorll() {
        binding.rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var prodNm = binding.etSearch.text.toString()
                // 스크롤이 끝에 도달했는지 확인
                if (!binding.rvSearchResult.canScrollVertically(1)) {
                    lastIndex += 1
                    Log.d("스크롤최하단",lastIndex.toString())
                    purchaseViewModel.purchaseLists(prodGb.toString(),prodNm,0,lastIndex)
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}