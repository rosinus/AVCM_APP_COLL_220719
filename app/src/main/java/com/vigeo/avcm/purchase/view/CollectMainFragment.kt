package com.vigeo.avcm.purchase.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vigeo.avcm.databinding.*
import com.vigeo.avcm.purchase.adapter.CollectAdapter
import com.vigeo.avcm.purchase.viewmodel.CollectViewModel


class CollectMainFragment : Fragment() {

    private var _binding : FragmentCollectMainBinding? = null
    private val binding : FragmentCollectMainBinding get() = _binding!!

    lateinit var collectViewModel: CollectViewModel
    lateinit var collectAdapter: CollectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectMainBinding.inflate(inflater, container, false)

        collectViewModel = (activity as CollectActivity).collectViewModel

        setupRecyclerView()

        Log.d("프레그먼트","진입")
        collectViewModel.result.observe(viewLifecycleOwner){ response ->
            val purchase = response.collectList

            collectAdapter.submitList(purchase)
        }

        collectViewModel.collectionLists("1")

//        binding.btnOk.setOnClickListener{
//            collectViewModel.collectionLists("1")
//        }

        return binding.root
    }

    private fun setupRecyclerView() {
        collectAdapter = CollectAdapter()
        binding.rvSearchResult3.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL

                )
            )
            adapter = collectAdapter
        }
    }
}