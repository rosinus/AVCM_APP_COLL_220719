package com.vigeo.avcm.purchase.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityPurchaseBinding
import com.vigeo.avcm.databinding.FragmentPurchaseOkBinding
import com.vigeo.avcm.databinding.ItemPurchaseDetailBinding
import com.vigeo.avcm.databinding.PopJoinOkBinding
import com.vigeo.avcm.login.view.LoginActivity
import com.vigeo.avcm.purchase.data.model.Purchase
import com.vigeo.avcm.purchase.repository.PurchaseRepositoryImpl
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModelProviderFactory


class PurchaseActivity : AppCompatActivity() {
    private val binding: ActivityPurchaseBinding by lazy {
        ActivityPurchaseBinding.inflate(layoutInflater)
    }
    lateinit var purchaseViewModel: PurchaseViewModel
    var prodNo : String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val purchaseRepository = PurchaseRepositoryImpl()
        val factory = PurchaseViewModelProviderFactory(purchaseRepository)
        purchaseViewModel = ViewModelProvider(this, factory)[PurchaseViewModel::class.java]

        if (intent.hasExtra("prodNo")) {
            prodNo = intent.getStringExtra("prodNo").toString()
        } else {
        }
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_frame, PurchaseMainFragment()).commit()

    }


}