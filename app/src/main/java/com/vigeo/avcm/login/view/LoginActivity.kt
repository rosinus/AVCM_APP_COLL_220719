package com.vigeo.avcm.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityLoginBinding
import com.vigeo.avcm.databinding.PopFormatPwBinding
import com.vigeo.avcm.databinding.PopFormatPwNoBinding
import com.vigeo.avcm.databinding.PopFormatPwOkBinding
import com.vigeo.avcm.join.view.JoinActivity
import com.vigeo.avcm.main.view.MainActivity

class LoginActivity  : AppCompatActivity() {

    private val loginBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        Log.d("Login : ", "Login - onCreate() called")


        //로그인 버튼 누를 시
        loginBinding.btnLoginOk.setOnClickListener {
            //계정 존재 여부 API 호출 ( 리턴타입: 불리언 )
            val isUserExist = false;

            if(isUserExist){ //존재할 시

                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{ //존재하지 않을 시
                formatNoDialog()
            }
        }

        //회원가입 버튼 누를 시
        loginBinding.btnJoin.setOnClickListener {

            val intent: Intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        //비밀번호 초기화 버튼 누를 시 비밀번호 초기화 팝업 노출
        loginBinding.tvFormatPw.setOnClickListener {

            //비밀번호 초기화 팝업을 현재 레이아웃 위에 다이얼로그로 생성
            val dialogView : View = layoutInflater.inflate(R.layout.pop_format_pw, null)
            val alertDialog : AlertDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            //다이얼로그로 뷰바인딩
            //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
            val formatPwBinding: PopFormatPwBinding by lazy {
                PopFormatPwBinding.bind(dialogView)
            }

            //닫기 버튼 누를 시
            formatPwBinding.btnClose.setOnClickListener {
                alertDialog.dismiss()
            }

            //비밀번호 초기화 버튼 누를 시
            formatPwBinding.btnFormatCheck.setOnClickListener {

                //API에 보낼 파라미터
                val username = formatPwBinding.etName.text
                val phnum = formatPwBinding.etPhoneNum.text
                val userGb = "04"; //농민

                //API 호출 (리턴타입: 불리언)
                val isExistUser = true;

                if(isExistUser){  //해당하는 계정정보 있을 시

                    alertDialog.dismiss() //다이얼로그 닫기
                    //사용자 비밀번호 초기화 API 호출 (리턴타입: 불리언)
                    val isSuccessful = true;
                    if(isSuccessful){ //초기화 성공적일 시
                        //초기화 성공 팝업 호출
                        formatOkDialog(phnum)
                    }else{ //초기화 실패 시

                        //문제 발생 오류 팝업 호출
                    }

                }else{ //해당하는 계정정보 없을 시

                    //계정 존재 안함 팝업 호출
                    formatNoDialog()
                }
            }

            alertDialog.show()

        }

    }

    //비밀번호 초기화 완료 팝업
    fun formatOkDialog(tv : Editable?){
        //비밀번호 초기화 완료 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val formatOkDialogView : View = layoutInflater.inflate(R.layout.pop_format_pw_ok, null)
        val formatOkAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(formatOkDialogView)
            .create()
        formatOkAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val formatPwOkBinding: PopFormatPwOkBinding by lazy {
            PopFormatPwOkBinding.bind(formatOkDialogView)
        }

        //닫기 버튼 누를 시
        formatPwOkBinding.btnPwFormatOk.setOnClickListener {

            //비밀번호 초기화 시 사용했던 연락처를 로그인 화면 연락처란에 넣기
            loginBinding.etLoginPhoneNum.text = tv

            formatOkAlertDialog.dismiss()
        }

    }

    //계정 정보 존재하지 않을 때 팝업
    fun formatNoDialog(){

        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val formatNoDialogView : View = layoutInflater.inflate(R.layout.pop_format_pw_no, null)
        val formatNoAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(formatNoDialogView)
            .create()
        formatNoAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val formatPwNoBinding: PopFormatPwNoBinding by lazy {
            PopFormatPwNoBinding.bind(formatNoDialogView)
        }

        formatPwNoBinding.btnReturnPwFormat.setOnClickListener{
            formatNoAlertDialog.dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        finish()
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}