package com.vigeo.avcm.myInfo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vigeo.avcm.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {

    private lateinit var faqbinding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        faqbinding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(faqbinding.root)


        //뒤로가기
        faqbinding.faqGoBack.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            finish()
        }


    }

}