package com.vigeo.avcm.collect.view.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityCollectNumBinding
import com.vigeo.avcm.databinding.ActivityMainBinding

class CollectNumActivity : AppCompatActivity() {
    private val binding: ActivityCollectNumBinding by lazy {
        ActivityCollectNumBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, CollectSelectFragment()).commit()
        }
    }
    }
