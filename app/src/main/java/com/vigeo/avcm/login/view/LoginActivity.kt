package com.vigeo.avcm.login.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityLoginBinding

class LoginActivity  : AppCompatActivity() {

    private val loginbinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Login : ", "Login - onCreate() called")

        setContentView(loginbinding.root)

        loginbinding.btnLoginOk.setOnClickListener {
            //유저가 입력한 데이터와 서버 데이터 일치 확인

            //일치하면 main화면 이동
            //일치 안 하면
        }

    }
}