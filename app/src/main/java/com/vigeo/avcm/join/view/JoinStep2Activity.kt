package com.vigeo.avcm.join.view

import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityJoinStep1Binding
import com.vigeo.avcm.databinding.ActivityJoinStep2Binding

class JoinStep2Activity : AppCompatActivity() {

    private val step2binding: ActivityJoinStep2Binding by lazy {
        ActivityJoinStep2Binding.inflate(layoutInflater)
    }
    private val step1binding: ActivityJoinStep1Binding by lazy {
        ActivityJoinStep1Binding.inflate(layoutInflater)
    }


}