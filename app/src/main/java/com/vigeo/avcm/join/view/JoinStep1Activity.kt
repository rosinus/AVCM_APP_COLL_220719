package com.vigeo.avcm.join.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityJoinStep1Binding

class JoinStep1Activity : AppCompatActivity() {

    private val joinStep1Binding: ActivityJoinStep1Binding by lazy {
        ActivityJoinStep1Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(joinStep1Binding.root)
    }
}