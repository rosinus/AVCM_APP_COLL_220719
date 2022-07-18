package com.vigeo.avcm.purchase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityPurchaseBinding
import com.vigeo.avcm.databinding.FragmentPurchaseOkBinding
import com.vigeo.avcm.databinding.PopJoinOkBinding
import com.vigeo.avcm.login.view.LoginActivity
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
            purchaseOkDialog()
        }
    }

    //가입 완료 시 팝업
    fun purchaseOkDialog(){

        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val purchaseDialogView : View = layoutInflater.inflate(R.layout.fragment_purchase_ok, null)
        val purchaseAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(purchaseDialogView)
            .create()
        purchaseAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val purchaseBinding: FragmentPurchaseOkBinding by lazy {
            FragmentPurchaseOkBinding.bind(purchaseDialogView)
        }

        purchaseBinding.btnOk.setOnClickListener{
        }

        purchaseBinding.btnOk.setOnClickListener{
            
        }
    }

}