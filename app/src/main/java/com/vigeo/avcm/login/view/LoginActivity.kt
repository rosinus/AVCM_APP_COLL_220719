package com.vigeo.avcm.login.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.*
import com.vigeo.avcm.join.view.JoinActivity
import com.vigeo.avcm.login.model.LoginVO
import com.vigeo.avcm.login.model.UserObject
import com.vigeo.avcm.login.service.LoginService
import com.vigeo.avcm.main.view.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity  : AppCompatActivity() {

    private val loginBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)
        Log.d("Login : ", "Login - onCreate() called")

        //회원가입 완료 후 로그인 화면으로 넘어올 때
        //회원가입 시 입력했던 연락처 정보를 로그인화면에 입력함
        if(intent.hasExtra("userId")) {
            loginBinding.etLoginPhoneNum.setText(intent.getStringExtra("userId"))
        }

        //로그인 버튼 누를 시
        loginBinding.btnLoginOk.setOnClickListener {

            /* retrofit DB 연결 */
            val gson : Gson = gsonCreate()
            val retrofit = retrofitBuild(gson)

            //계정 존재 여부 API 호출 ( 리턴타입: 불리언 )
            isUserLogin(retrofit);
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

                /* retrofit DB 연결 */
                val gson : Gson = gsonCreate()
                val retrofit = retrofitBuild(gson)

                //API에 보낼 파라미터
                val userNm = formatPwBinding.etName.text.toString()
                val userId = formatPwBinding.etPhoneNum.text.toString()

                val phoneNum = formatPwBinding.etPhoneNum.text //보내는 데이터는 아니지만 사용할 곳이 있음.

                //아이디 존재 여부 검사
                retrofit.create(LoginService::class.java).isUserPwUpdate(
                    userId = userId,
                    userNm = userNm
                ).enqueue(object :
                    Callback<LoginVO> {
                    override fun onResponse(call: Call<LoginVO>, response: Response<LoginVO>) {

                        var userVO = response.body()!!
                        val result = userVO.isUser //userChack
                        val errorMsg = userVO.errorMsg //오류내용
                        if(response.isSuccessful){

                            if(result){ //업데이트 성공

                                alertDialog.dismiss() //다이얼로그 닫기
                                formatOkDialog(phoneNum)
                                alertDialog.dismiss()

                                Log.d("\"Login // isUserPwUpdate : ", "onResponse 성공: result :: $result")

                            }else{ //업데이트 실패

                                formatNoDialog()

                                Log.d("\"Login // isUserPwUpdate : ","사용자 정보 업데이트 실패, 오류 내용:$errorMsg")
                            }
                            // 정상적으로 통신이 성공된 경우
                            Log.d("\"Login // isUserPwUpdate : ", "onResponse 성공: " + userVO.toString())

                        }else{
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("\"Login // isUserPwUpdate : ", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<LoginVO>, t: Throwable) {
                        Log.d("Login // isUserLogin : ", "onResponse 실패")
                    }
                })
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



    //오류 시 팝업
    fun errorDialog(text: String){

        val errorDialogView : View = layoutInflater.inflate(R.layout.pop_error, null)
        val errorAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(errorDialogView)
            .create()

        val errorBinding: PopErrorBinding by lazy {
            PopErrorBinding.bind(errorDialogView)
        }
        errorBinding.tvContent.text = text

        errorAlertDialog.show()

        errorBinding.btnOk.setOnClickListener {
            errorAlertDialog.dismiss()

        }
    }


    private fun isUserLogin(retrofit: Retrofit) {

        val userId = loginBinding.etLoginPhoneNum.text.toString()
        val userPw = loginBinding.etLoginPw.text.toString()

        //아이디 존재 여부 검사
        retrofit.create(LoginService::class.java).isUserLogin(
            userId = userId,
            userPw = userPw
        ).enqueue(object :
            Callback<LoginVO> {
            override fun onResponse(call: Call<LoginVO>, response: Response<LoginVO>) {

                var userVO = response.body()!!
                val result = userVO.isUser

                if(response.isSuccessful){

                    if(result == true){ //로그인 성공

                        val userObject : UserObject? = userVO.userInfo
                        //기기에 사용자 정보 저장
                        //내부 저장소인 SharedPreferences 에 값을 저장하는 방법, 앱의 데이터를 삭제하기 전까지는 존재한다.
                        //vigeo란 이름으로 Context.MODE_PRIVATE :내부 앱에서만 사용가능한 방법으로 저장함
                        val sharedPreference = getSharedPreferences("user", Context.MODE_PRIVATE)

                        //Editor (Key, Value)형식으로 저장하기 위함
                        //editor.putXXX 형식으로 형식에 맞춰서 넣어줘야함.
                        //반드시 commit을 해줘야함.
                        val editor : SharedPreferences.Editor = sharedPreference.edit()

                        if (userObject != null) {
                            editor.putString("userNo",userObject.userNo)
                            editor.putString("userNm",userObject.userNm)
                            editor.putString("phoneNum",userObject.phoneNum)
                            editor.putString("zipCd",userObject.zipCd)
                            editor.putString("addr",userObject.addr)
                            editor.putString("addrDetail",userObject.addrDetail)
                            editor.putString("fcmToken",userObject.fcmToken)
                        }

                        editor.commit()

                        val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else{ //로그인 실패

                        return errorDialog("연락처 또는 비밀번호가 일치하지 않습니다.")

                    }
                    // 정상적으로 통신이 성공된 경우
                    Log.d("\"Login // isUserLogin : ", "onResponse 성공: " + userVO.toString())

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"Login // isUserLogin : ", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<LoginVO>, t: Throwable) {
                Log.d("Login // isUserLogin : ", "onResponse 실패")
            }
        })
    }

    private fun gsonCreate() : Gson {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        return gson
    }

    private fun retrofitBuild(gson : Gson) : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.192:8087/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

}