package com.vigeo.avcm.purchase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentPurchaseMainBinding
import com.vigeo.avcm.databinding.FragmentPurchaseOkBinding
import com.vigeo.avcm.databinding.ItemPurchaseDetailBinding

class PurchaseOkFragment : Fragment() {
    private var _okBinding : FragmentPurchaseOkBinding? = null
    private val okBinding : FragmentPurchaseOkBinding get() = _okBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _okBinding = FragmentPurchaseOkBinding.inflate(inflater, container, false)

        return okBinding.root
    }

}