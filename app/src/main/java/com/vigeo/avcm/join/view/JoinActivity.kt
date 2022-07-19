package com.vigeo.avcm.join.view

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityJoinBinding
import com.vigeo.avcm.databinding.PopErrorBinding
import com.vigeo.avcm.databinding.PopJoinOkBinding
import com.vigeo.avcm.join.model.JoinVO
import com.vigeo.avcm.join.service.JoinService
import com.vigeo.avcm.login.view.LoginActivity
import com.vigeo.avcm.myInfo.view.MyWebView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JoinActivity : AppCompatActivity() {

    private val joinBinding: ActivityJoinBinding by lazy {
        ActivityJoinBinding.inflate(layoutInflater)
    }

    var client: WebViewClient = object : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return false
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(joinBinding.root)

        //우편번호 선택 시 다음 우편 API 팝업 호출
        joinBinding.etJoinPostNum.setOnClickListener{
            daumAddrClick()
        }

        //주소 선택 시 다음 우편 API 팝업 호출
        joinBinding.etJoinAddr.setOnClickListener{
            daumAddrClick()
        }

        //다음 버튼 눌렀을 때
        joinBinding.btnGoStep2.setOnClickListener {
            //val intent = Intent(this@JoinStep1Activity, JoinStep2Activity::class.java)
           //startActivity(intent)

            //STEP1 화면 숨기기
            joinBinding.joinStep1Layout.visibility = View.GONE
            //STEP2 화면 보이기
            joinBinding.joinStep2Layout.visibility = View.VISIBLE
        }

        //STEP1 화면에서 뒤로가기 눌렀을 때
        joinBinding.tvPrev.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //현재화면 닫기 -> 로그인 화면
            finish()
        }

        //STEP2 화면에서 뒤로가기 눌렀을 때
        joinBinding.tvPrev2.setOnClickListener {
            //STEP2 화면 숨기기
            joinBinding.joinStep2Layout.visibility = View.GONE
            //STEP1 화면 보이기
            joinBinding.joinStep1Layout.visibility = View.VISIBLE
        }



        //가입 버튼 눌렀을 시
        joinBinding.btnJoin.setOnClickListener {

            /* retrofit DB 연결 */
            val gson : Gson = gsonCreate()
            val retrofit = retrofitBuild(gson)

            val userId = joinBinding.etJoinPhoneNum.text.toString()
            val userNm = joinBinding.etJoinName.text
            val postNum = joinBinding.etJoinPostNum.text
            val addr = joinBinding.etJoinAddr.text
            val daddr = joinBinding.etJoinDaddr.text

            if(userId.length  < 11){
                //STEP2 화면 숨기기
                joinBinding.joinStep2Layout.visibility = View.GONE
                //STEP1 화면 보이기
                joinBinding.joinStep1Layout.visibility = View.VISIBLE
                return@setOnClickListener errorDialog("연락처 형태를 확인해주세요.")
            }
            if(userNm.isEmpty()){
                //STEP2 화면 숨기기
                joinBinding.joinStep2Layout.visibility = View.GONE
                //STEP1 화면 보이기
                joinBinding.joinStep1Layout.visibility = View.VISIBLE
                return@setOnClickListener errorDialog("성명을 입력하세요.")
            }
            if(postNum.isEmpty()){
                return@setOnClickListener errorDialog("우편번호를 입력하세요.")
            }
            if(postNum.isEmpty()){
                return@setOnClickListener errorDialog("우편번호를 입력하세요.")
            }
            if(addr.isEmpty()){
                return@setOnClickListener errorDialog("주소를 입력하세요.")
            }
            if(daddr.isEmpty()){
                return@setOnClickListener errorDialog("상세 주소를 입력하세요.")
            }

            //존재하는 아이디인지 확인 및 추가
            isUserExist(retrofit)

        }
    }


    //오류 시 팝업
    fun errorDialog(text: String){

        val errorDialogView : View = layoutInflater.inflate(R.layout.pop_error, null)
        val errorAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(errorDialogView)
            .setCancelable(false)
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


    //가입 완료 시 팝업
    fun joinOkDialog(){

        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val joinOkDialogView : View = layoutInflater.inflate(R.layout.pop_join_ok, null)
        val joinOkAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(joinOkDialogView)
            .setCancelable(false)
            .create()
        joinOkAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val joinOkBinding: PopJoinOkBinding by lazy {
            PopJoinOkBinding.bind(joinOkDialogView)
        }

        joinOkBinding.btnOk.setOnClickListener{
            joinOkAlertDialog.dismiss()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("userId", joinBinding.etJoinPhoneNum.text.toString())
            startActivity(intent)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //현재화면 닫기 -> 로그인 화면
            finish()
        }
        return false
    }

    private fun userInsert(retrofit: Retrofit) {

        val userId = joinBinding.etJoinPhoneNum.text.toString()
        val userPw = joinBinding.etJoinPhoneNum.text.toString()
        val zipCd = joinBinding.etJoinPostNum.text.toString()
        val addr = joinBinding.etJoinAddr.text.toString()
        val addrDetail = joinBinding.etJoinDaddr.text.toString()
        val userNm = joinBinding.etJoinName.text.toString()


        //아이디 중복만 검사
        retrofit.create(JoinService::class.java).userInsert(
            userId = userId,
            userPw = userPw, //초기 비밀번호는 초기 아이디(=초기 연락처)와 동일
            zipCd = zipCd,
            addr = addr,
            addrDetail = addrDetail,
            userNm = userNm
        ).enqueue(object :
            Callback<JoinVO> {
            override fun onResponse(call: Call<JoinVO>, response: Response<JoinVO>) {

                var joinVO = response.body()!!
                val result = joinVO.result.toBoolean()

                if(response.isSuccessful){
                    if(result){ //성공적으로 추가됨
                        //회원가입 완료 팝업 호출
                        return joinOkDialog()
                    }else{
                        //오류 팝업 호출
                        return errorDialog("오류가 발생하였습니다.\n관리자에게 문의하세요.")
                    }
                    // 정상적으로 통신이 성공된 경우
                    Log.d("\"Join // userInsert : ", "onResponse 성공: " + joinVO.toString())
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"Join // userInsert : ", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<JoinVO>, t: Throwable) {
                Log.d("Join // userInsert : ", "onResponse 실패")
            }
        })
    }


    private fun isUserExist(retrofit: Retrofit) {
        val userId = joinBinding.etJoinPhoneNum.text.toString()

        //아이디 중복만 검사
        retrofit.create(JoinService::class.java).isUserExist(
            userId = userId,
            userPw = null,
            userGb = null
        ).enqueue(object :
            Callback<JoinVO> {
            override fun onResponse(call: Call<JoinVO>, response: Response<JoinVO>) {

                var joinVO = response.body()!!
                val result = joinVO.isUser

                if(response.isSuccessful){

                    if(result){ //아이디 중복
                        //아이디 중복 팝업 호출
                       return errorDialog("이미 등록된 연락처입니다.\n 다시 확인해주세요.")

                    }else{
                       return userInsert(retrofit)
                    }
                    // 정상적으로 통신이 성공된 경우
                    Log.d("\"Join // isUserExist : ", "onResponse 성공: " + joinVO.toString())

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"Join // isUserExist : ", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<JoinVO>, t: Throwable) {
                Log.d("Join // isUserExist : ", "onResponse 실패")
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


    //우편번호, 주소 클릭시 연결 함수
    fun daumAddrClick() {
        var daumWebView: WebView = MyWebView(this)
        daumWebView.isFocusable = true
        daumWebView.isFocusableInTouchMode = true
        Log.d("우편번호 누름", "우편번호 눌렀음.")

        Log.d("웹뷰 실행", "웹뷰실행")
        val alertDialog: AlertDialog = AlertDialog.Builder(this)
            .setView(daumWebView)
            //.setCancelable(false)
            .create()

        //daumWebView = webDaumAddrBinding.daumWebView
        daumWebView.apply {
            WebView.setWebContentsDebuggingEnabled(true)
            settings.javaScriptEnabled = true
            isFocusableInTouchMode = true
            addJavascriptInterface(JoinActivity.AndroidBridge(joinBinding, alertDialog), "avcmApp")
            //settings.setSupportMultipleWindows(true)
            //settings.domStorageEnabled = true
            loadUrl("http://192.168.0.193:8080/appApi/loginApp/daumAddr.do")
            //loadUrl("http://snur.vigeotech.com/com/login/login.do")
            webViewClient = client
            webChromeClient = WebChromeClient()

        }

        daumWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                if (handler != null) {
                    handler.proceed()
                };
            }

            override fun onPageFinished(view: WebView, url: String?) {
                view.loadUrl("javascript:daumPostcode();")
            }
        }

        alertDialog.show()
        Log.d("웹뷰 실행 끝", "웹뷰 실행 끝")
    }

    //웹앱 js와 브릿지 - 우편번호 등등
    class AndroidBridge(val joinBinding: ActivityJoinBinding, val alertDialog: AlertDialog) {

        @JavascriptInterface
        fun sendAddr(
            zonecode: String = "null",
            address: String = "null",
            buildingName: String = "null"
        ) {
            joinBinding.etJoinPostNum.setText(zonecode)
            joinBinding.etJoinAddr.setText(address)
            joinBinding.etJoinDaddr.setText(buildingName)
            alertDialog.dismiss()
        }
    }

}

//WebView에서 inputText 클릭시 입력 키보드 안뜨는 문제를 해결하기위해 onCheckIsTextEditor을 재정의 하기 위함.
internal class MyWebView(context: Context?) : WebView(context!!) {
    override fun onCheckIsTextEditor(): Boolean {
        return true
    }
}