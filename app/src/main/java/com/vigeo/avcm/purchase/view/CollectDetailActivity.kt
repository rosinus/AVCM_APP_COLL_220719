package com.vigeo.avcm.purchase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vigeo.avcm.databinding.ActivityCollectDetailBinding
import com.vigeo.avcm.databinding.ActivityCollectListBinding

class CollectDetailActivity : AppCompatActivity() {
    private val binding: ActivityCollectDetailBinding by lazy {
        ActivityCollectDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}