package com.vigeo.avcm.myInfo.view

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityMyInfoBinding


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
        val editor : SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("userId","01012345678")        //아이디
        editor.putString("userNm","김농민")        //이름
        editor.putString("zipCd","13494")         //우편번호
        editor.putString("addr","경기 성남시 분당구 판교역로 235")          //주소
        editor.putString("addrDetail","에이치스퀘어 엔동")    //상세주소
        editor.putString("phoneNum","01012345678")      //전화번호
        //editor.putString("fcmToken","하태훈")      //Fcm 토큰값
        editor.commit()

        //여기서 null!!은 데이터가 test1가 비어있을때 리턴해줌.
        //vigeo - test1이 있을때는 위에 저장해준 test2가 나옴
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

        //우편번호 테스트
        myInfoBinding.myInfoZipCd2.setOnClickListener {

            var daumWebView: WebView = MyWebView(this)
            daumWebView.isFocusable = true
            daumWebView.isFocusableInTouchMode = true
            Log.d("우편번호 누름" , "우편번호 눌렀음.")

            Log.d("웹뷰 실행?" , "웹뷰실행????????")
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

            Log.d("웹뷰 실행?" , "웹뷰실행")
            //daumWebView!!.loadUrl("http://192.168.0.193:8080/appApi/loginApp/daumAddr.do")


            alertDialog.show()

        }

        //삭제하는법, 커밋해줘야함
        //editor.remove("test1")
        //editor.commit()

        //초기화
        //editor.clear()

    }

    //웹앱 js와 브릿지
    class AndroidBridge2(myInfoBinding : ActivityMyInfoBinding, alertDialog: AlertDialog) {
        val myInfoBinding = myInfoBinding
        val alertDialog = alertDialog
        @JavascriptInterface
        fun sendAddr(zonecode: String = "null", address: String = "null", buildingName: String = "null") {
            myInfoBinding.myInfoZipCd.setText(zonecode)
            myInfoBinding.myInfoAddr.setText(address)
            myInfoBinding.myInfoAddrDetail.setText(buildingName)
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