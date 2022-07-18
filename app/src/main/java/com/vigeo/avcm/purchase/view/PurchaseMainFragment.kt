package com.vigeo.avcm.purchase.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vigeo.avcm.databinding.FragmentPurchaseMainBinding
import com.vigeo.avcm.purchase.adapter.PurchaseDetailAdapter
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel


class PurchaseMainFragment : Fragment() {

    private var _binding : FragmentPurchaseMainBinding? = null
    private val binding : FragmentPurchaseMainBinding get() = _binding!!

    lateinit var purchaseViewModel: PurchaseViewModel
    lateinit var purchaseDetailAdapter: PurchaseDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseMainBinding.inflate(inflater, container, false)

        purchaseViewModel = (activity as PurchaseActivity).purchaseViewModel

        var prodNo = (activity as PurchaseActivity).prodNo

        setupRecyclerView()

        purchaseViewModel.resultDetail.observe(viewLifecycleOwner){ response ->
            val purchase = response.purchaseList
            purchaseDetailAdapter.submitList(purchase)
        }
        purchaseViewModel.purchaseDetail(prodNo)

        return binding.root
    }

    private fun setupRecyclerView() {
        purchaseDetailAdapter = PurchaseDetailAdapter()
        binding.rvSearchResult2.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = purchaseDetailAdapter
        }
    }
}