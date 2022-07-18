package com.vigeo.avcm.purchase.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityPurchaseBinding
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

        binding.tvPrev.setOnClickListener {
            Log.d("클릭", "구매신청 으로 이동")
            finish()
        }

        binding.btnOk.setOnClickListener(){
            
        }
    }

}