package com.vigeo.avcm.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityLoginBinding
import com.vigeo.avcm.join.view.JoinStep1Activity
import com.vigeo.avcm.main.view.MainActivity

class LoginActivity  : AppCompatActivity() {

    private val loginBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        Log.d("Login : ", "Login - onCreate() called")


        /** 로그인 버튼 누를 시
         * */
        loginBinding.btnLoginOk.setOnClickListener {
            //유저가 입력한 데이터와 서버 데이터 일치 확인
            Toast.makeText(this@LoginActivity, "로그인 시도!", Toast.LENGTH_SHORT).show()

            //일치하면 main화면 이동
            val intent: Intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

            finish()
            //일치 안 하면
        }


        /** 회원가입 버튼 누를 시
         * */
        loginBinding.btnJoin.setOnClickListener {

            Toast.makeText(this@LoginActivity, "회원가입 시도!", Toast.LENGTH_SHORT).show()

            val intent: Intent = Intent(this@LoginActivity, JoinStep1Activity::class.java)

            startActivity(intent)
        }


        /** 비밀번호 초기화
         * */
        loginBinding.tvFormatPw.setOnClickListener {

        }

    }
}