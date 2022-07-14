package com.vigeo.avcm.myInfo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vigeo.avcm.databinding.ActivityFaqBinding
import com.vigeo.avcm.databinding.ActivityGuideVideoBinding
import com.vigeo.avcm.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {

    private lateinit var myInfoBinding: ActivityMyInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myInfoBinding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(myInfoBinding.root)

    }
}