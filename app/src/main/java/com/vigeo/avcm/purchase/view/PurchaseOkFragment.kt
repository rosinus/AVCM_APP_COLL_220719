package com.vigeo.avcm.purchase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentPurchaseBinding
import com.vigeo.avcm.databinding.FragmentPurchaseOkBinding

class PurchaseOkFragment : Fragment() {
    private var _binding : FragmentPurchaseOkBinding? = null
    private val binding : FragmentPurchaseOkBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentPurchaseOkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}