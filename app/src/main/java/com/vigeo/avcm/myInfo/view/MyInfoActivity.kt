package com.vigeo.avcm.myInfo.view

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityMyInfoBinding
import com.vigeo.avcm.databinding.PopErrorBinding
import com.vigeo.avcm.databinding.PopFormatPwOkBinding
import com.vigeo.avcm.login.view.PopFormatPwOk
import com.vigeo.avcm.myInfo.service.MyInfoService
import com.vigeo.avcm.myInfo.viewModel.MyInfoVO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyInfoActivity : AppCompatActivity() {

    val myInfoBinding: ActivityMyInfoBinding by lazy { ActivityMyInfoBinding.inflate(layoutInflater) }

    var client: WebViewClient = object : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myInfoBinding.root)

        //내부 저장소인 SharedPreferences 에 값을 저장하는 방법, 앱의 데이터를 삭제하기 전까지는 존재한다.
        //vigeo란 이름으로 Context.MODE_PRIVATE : 내부 앱에서만 사용가능한 방법으로 저장함
        val sharedPreference = getSharedPreferences("user", Context.MODE_PRIVATE)

        //Editor (Key, Value)형식으로 저장하기 위함
        //editor.putXXX 형식으로 형식에 맞춰서 넣어줘야함.
        //반드시 commit을 해줘야함.
        /*val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("userNo", "2")        //아이디
        editor.putString("userId", "01012345678")        //아이디
        editor.putString("userNm", "김농민")        //이름
        editor.putString("zipCd", "13494")         //우편번호
        editor.putString("addr", "경기 성남시 분당구 판교역로 235")          //주소
        editor.putString("addrDetail", "에이치스퀘어 엔동")    //상세주소
        editor.putString("phoneNum", "01012345678")      //전화번호
        //editor.putString("fcmToken","하태훈")      //Fcm 토큰값
        editor.commit()*/

        //여기서 null!!은 데이터가 test1가 비어있을때 리턴해줌.
        //vigeo - test1이 있을때는 위에 저장해준 test2가 나옴
        val userNo = sharedPreference.getString("userNo", "null").toString()
        val userId = sharedPreference.getString("userId", "null")
        val userNm = sharedPreference.getString("userNm", "null")
        val zipCd = sharedPreference.getString("zipCd", "null")
        val addr = sharedPreference.getString("addr", "null")
        val addrDetail = sharedPreference.getString("addrDetail", "null")
        val phoneNum = sharedPreference.getString("phoneNum", "null")
        val fcmToken = sharedPreference.getString("fcmToken", "null")

        myInfoBinding.myInfoId.setText(userId)
        myInfoBinding.myInfoName.setText(userNm)
        myInfoBinding.myInfoZipCd.setText(zipCd)
        myInfoBinding.myInfoAddr.setText(addr)
        myInfoBinding.myInfoAddrDetail.setText(addrDetail)

        //우편번호 클릭시
        myInfoBinding.myInfoZipCd.setOnClickListener {
            daumAddrClick()
        }

        //주소 클릭시
        myInfoBinding.myInfoAddr.setOnClickListener {
            daumAddrClick()
        }

        //확인 클릭시
        myInfoBinding.myInfoOkButton.setOnClickListener {

            var changeCheck: Boolean = false
            var updateUserNo: String = userNo
            var updateUserId: String = ""
            var updateUserPw: String = ""
            var updateUserNm: String = ""
            var updateZipCd: String = ""
            var updateAddr: String = ""
            var updateAddrDetail: String = ""

            //아이디 변경 체크
            if (userId != myInfoBinding.myInfoId.text.toString()) {
                updateUserId = myInfoBinding.myInfoId.text.toString()
                changeCheck = true
            }

            //비밀번호 변경여부 체크 - 비밀번호 입력, 비밀번호 확인 미입력
            if (myInfoBinding.myInfoPw.text.isNotEmpty() && myInfoBinding.myInfoPwCheck.text.isEmpty()) {
                Log.d("비밀번호 확인 : ", "비밀번호 입력, 비밀번호 확인 미입력")
                return@setOnClickListener Toast.makeText(
                    this,
                    "비밀번호 확인을 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()

            }

            //비밀번호 변경여부 체크 - 비밀번호 미입력, 비밀번호 확인 입력
            if (myInfoBinding.myInfoPw.text.isEmpty() && myInfoBinding.myInfoPwCheck.text.isNotEmpty()) {
                Log.d("비밀번호 확인 : ", "비밀번호 미입력, 비밀번호 확인 입력")
                return@setOnClickListener Toast.makeText(this, "비밀번호 입력해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            //비밀번호 변경여부 체크 - 둘다 입력
            if (myInfoBinding.myInfoPw.text.isNotEmpty() && myInfoBinding.myInfoPwCheck.text.isNotEmpty()) {
                Log.d("비밀번호 확인 : ", "둘다 입력 확인")
                //비밀번호와 비밀번호 체크가 일치할때
                if (myInfoBinding.myInfoPw.text.toString() == myInfoBinding.myInfoPwCheck.text.toString()) {
                    Log.d("비밀번호 확인 : ", "비밀번호 일치 확인")
                    updateUserPw = myInfoBinding.myInfoPw.text.toString()
                    changeCheck = true

                    //비밀번호가 일치하지 않을때
                } else {
                    Log.d("비밀번호 확인 : ", "비밀번호 미일치")
                    return@setOnClickListener Toast.makeText(
                        this,
                        "비밀번호 일치하지 않습니다. \n 다시한번 확인해주십시요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //이름 변경 체크
            if (userNm != myInfoBinding.myInfoName.text.toString()) {
                updateUserNm = myInfoBinding.myInfoName.text.toString()
                changeCheck = true
            }

            //우편번호 변경 체크
            if (zipCd != myInfoBinding.myInfoZipCd.text.toString()) {
                updateZipCd = myInfoBinding.myInfoZipCd.text.toString()
                changeCheck = true
            }

            //주소 변경 체크
            if (addr != myInfoBinding.myInfoAddr.text.toString()) {
                updateAddr = myInfoBinding.myInfoAddr.text.toString()
                changeCheck = true
            }

            //주소상세 변경 체크
            if (addrDetail != myInfoBinding.myInfoAddrDetail.text.toString()) {
                updateAddrDetail = myInfoBinding.myInfoAddrDetail.text.toString()
                changeCheck = true
            }

            //변경점이 있을 시 userUpdate 실행
            if (changeCheck) {
                userUpdate(sharedPreference, updateUserNo, updateUserId, updateUserPw, updateUserNm, updateZipCd, updateAddr, updateAddrDetail)
            }else{
                finish()
            }
        }

        //삭제하는법, 커밋해줘야함
        //editor.remove("test1")
        //editor.commit()

        //초기화
        //editor.clear()

    }

    //웹앱 js와 브릿지 - 우편번호 등등
    class AndroidBridge2(myInfoBinding: ActivityMyInfoBinding, alertDialog: AlertDialog) {
        val myInfoBinding = myInfoBinding
        val alertDialog = alertDialog

        @JavascriptInterface
        fun sendAddr(
            zonecode: String = "null",
            address: String = "null",
            buildingName: String = "null"
        ) {
            myInfoBinding.myInfoZipCd.setText(zonecode)
            myInfoBinding.myInfoAddr.setText(address)
            myInfoBinding.myInfoAddrDetail.setText(buildingName)
            alertDialog.dismiss()
        }
    }

    fun userUpdate(
        sharedPreference : SharedPreferences,
        updateUserNo: String,
        updateUserId: String,
        updateUserPw: String,
        updateUserNm: String,
        updateZipCd: String,
        updateAddr: String,
        updateAddrDetail: String,
    ) {

        /* retrofit DB 연결 */
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.193:8080/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(MyInfoService::class.java).isUserUpdate(
            userNo = updateUserNo,
            userId = updateUserId,
            userPw = updateUserPw,
            userNm = updateUserNm,
            zipCd = updateZipCd,
            addr = updateAddr,
            addrDetail = updateAddrDetail
        ).enqueue(object :
            Callback<MyInfoVO> {
            override fun onResponse(call: Call<MyInfoVO>, response: Response<MyInfoVO>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var myinfoUserUpdate = response.body()!!
                    if(myinfoUserUpdate.message == "true"){

                        val editor: SharedPreferences.Editor = sharedPreference.edit()

                        //내부 저장소 데이터 변경
                        if(updateUserId != ""){
                            editor.putString("userId", updateUserId)
                        }
                        if(updateUserNm != ""){
                            editor.putString("userNm", updateUserNm)
                        }
                        if(updateZipCd != ""){
                            editor.putString("zipCd", updateZipCd)
                        }
                        if(updateAddr != ""){
                            editor.putString("addr", updateAddr)
                        }
                        if(updateAddrDetail != ""){
                            editor.putString("addrDetail", updateAddrDetail)
                        }

                        editor.commit()

                        Log.d("myinfo UserUpdate : ", "myinfo UserUpdate 성공: " + myinfoUserUpdate.toString())
                        successDialog("정보가 수정되었습니다. \n 이전 화면으로 돌아갑니다.", "확인")

                    }else{
                        Log.d("myinfo UserUpdate : ", "myinfo UserUpdate 실패: " + myinfoUserUpdate.toString());
                        errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
                    }
                } else {
                    errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("myinfo UserUpdate : ", "myinfo UserUpdate 실패")
                }
            }

            override fun onFailure(call: Call<MyInfoVO>, t: Throwable) {
                Log.d("myinfo UserUpdate : ", "myinfo UserUpdate 실패")
                errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
            }
        })
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

    //성공 시 팝업
    fun successDialog(context: String, button:String){

        val successDialogView : View = layoutInflater.inflate(R.layout.pop_format_pw_ok, null)
        val successAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(successDialogView)
            .create()

        val successBinding: PopFormatPwOkBinding by lazy {
            PopFormatPwOkBinding.bind(successDialogView)
        }

        successBinding.tvContent.text = context
        successBinding.btnPwFormatOk.text = button

        successAlertDialog.show()

        successBinding.btnPwFormatOk.setOnClickListener {
            successAlertDialog.dismiss()
            finish()
        }
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
            .create()

        //daumWebView = webDaumAddrBinding.daumWebView
        daumWebView.apply {
            WebView.setWebContentsDebuggingEnabled(true)
            settings.javaScriptEnabled = true
            isFocusableInTouchMode = true
            addJavascriptInterface(AndroidBridge2(myInfoBinding, alertDialog), "avcmApp")
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


}

//WebView에서 inputText 클릭시 입력 키보드 안뜨는 문제를 해결하기위해 onCheckIsTextEditor을 재정의 하기 위함.
internal class MyWebView(context: Context?) : WebView(context!!) {
    override fun onCheckIsTextEditor(): Boolean {
        return true
    }
}