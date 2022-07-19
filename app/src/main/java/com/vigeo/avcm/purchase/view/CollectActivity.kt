package com.vigeo.avcm.purchase.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.*
import com.vigeo.avcm.purchase.repository.CollectRepositoryImpl
import com.vigeo.avcm.purchase.repository.PurchaseRepositoryImpl
import com.vigeo.avcm.purchase.viewmodel.CollectViewModel
import com.vigeo.avcm.purchase.viewmodel.CollectViewModelProviderFactory
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModelProviderFactory


class CollectActivity : AppCompatActivity() {
    private val binding: ActivityCollectListBinding by lazy {
        ActivityCollectListBinding.inflate(layoutInflater)
    }
    lateinit var collectViewModel: CollectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val collectRepository = CollectRepositoryImpl()
        val factory = CollectViewModelProviderFactory(collectRepository)
        collectViewModel = ViewModelProvider(this, factory)[CollectViewModel::class.java]

        Log.d("액티비티","진입")
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_frame3, CollectMainFragment()).commit()
    }


}