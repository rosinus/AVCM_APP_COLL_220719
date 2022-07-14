package com.vigeo.avcm.login.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.PopFormatPwBinding

class PopFormatPw : AppCompatActivity() {

    private val formatpwbinding: PopFormatPwBinding by lazy {
        PopFormatPwBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(formatpwbinding.root)

        formatpwbinding.btnClose.setOnClickListener {

            val username = formatpwbinding.etName.text
            val phnum = formatpwbinding.etPhoneNum.text

            Toast.makeText(this, "TOAST_step1 username: $username , phnum: $phnum !!",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "PopFormatPw onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        finish()
        Toast.makeText(this, "PopFormatPw onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "PopFormatPw onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "PopFormatPw onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "PopFormatPw onDestroy", Toast.LENGTH_SHORT).show()
    }
}