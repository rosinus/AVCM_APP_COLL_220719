package com.vigeo.avcm.myInfo.view

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
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
import com.vigeo.avcm.databinding.PopUserDeleteBinding
import com.vigeo.avcm.login.view.LoginActivity
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

    /* retrofit DB 연결 */
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.193:8080/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myInfoBinding.root)

        //내부 저장소인 SharedPreferences 에 값을 저장하는 방법, 앱의 데이터를 삭제하기 전까지는 존재한다.
        //vigeo란 이름으로 Context.MODE_PRIVATE : 내부 앱에서만 사용가능한 방법으로 저장함
        val sharedPreference = getSharedPreferences("user", Context.MODE_PRIVATE)

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

        //뒤로가기
        myInfoBinding.myInfoGoBack.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            finish()
        }

        //로그아웃
        myInfoBinding.myInfoLogOut.setOnClickListener {
            Log.d("Myinfo", "로그아웃")

            //내부 저장소 데이터 삭제
            val editor : SharedPreferences.Editor = sharedPreference.edit()
            editor.remove("userNo")
            editor.commit()
            finishAffinity()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //중복확인 클릭시
        myInfoBinding.myInfoIdCheck.setOnClickListener {
            //아이디 변경 체크
            if (userId != myInfoBinding.myInfoId.text.toString()) {
                userIdCheck(context = this,myInfoBinding.myInfoId.text.toString())
            }else{
                return@setOnClickListener Toast.makeText(this,"사용가능한 아이디입니다.",Toast.LENGTH_SHORT).show()
            }
        }

        //우편번호 클릭시
        myInfoBinding.myInfoZipCd.setOnClickListener {
            daumAddrClick()
        }

        //주소 클릭시
        myInfoBinding.myInfoAddr.setOnClickListener {
            daumAddrClick()
        }

        //회원탈퇴 클릭시
        myInfoBinding.myInfoUserDelete.setOnClickListener {
            deleteDialog(userNo)
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
                return@setOnClickListener Toast.makeText(this,"비밀번호 확인을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }

            //비밀번호 변경여부 체크 - 비밀번호 미입력, 비밀번호 확인 입력
            if (myInfoBinding.myInfoPw.text.isEmpty() && myInfoBinding.myInfoPwCheck.text.isNotEmpty()) {
                Log.d("비밀번호 확인 : ", "비밀번호 미입력, 비밀번호 확인 입력")
                return@setOnClickListener Toast.makeText(this, "비밀번호 입력해주세요.", Toast.LENGTH_SHORT).show()
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
    }

    //아이디 중복확인 함수
    fun userIdCheck(
        context: Context,
        updateUserId: String
    ) {
        retrofit.create(MyInfoService::class.java).isUserExist(
            userId = updateUserId
        ).enqueue(object :
            Callback<MyInfoVO> {
            override fun onResponse(call: Call<MyInfoVO>, response: Response<MyInfoVO>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var myinfoUserIdCheck = response.body()!!
                    // true 일 경우는 중복된 아이디가 있음, false 일때는 중복된 아이디가 없음.
                    if(myinfoUserIdCheck.userChack == "true"){
                        Log.d("myinfo UserIdCheck : ", "myinfo UserIdCheck 성공: " + myinfoUserIdCheck.toString())
                        return Toast.makeText(context,"중복된 아이디가 있습니다. 아이디를 다시확인해 주십시요.",Toast.LENGTH_LONG).show()
                    }else{
                        Log.d("myinfo UserIdCheck : ", "myinfo UserIdCheck 실패: " + myinfoUserIdCheck.toString())
                        return Toast.makeText(context,"중복된 아이디가 없습니다. 사용가능합니다.",Toast.LENGTH_LONG).show()
                    }
                } else {
                    errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("myinfo UserIdCheck : ", "myinfo UserIdCheck 실패")
                }
            }

            override fun onFailure(call: Call<MyInfoVO>, t: Throwable) {
                Log.d("myinfo UserIdCheck : ", "myinfo UserIdCheck 실패")
                errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
            }
        })
    }

    //웹앱 js와 브릿지 - 우편번호, 주소 등등
    class AndroidBridge(val myInfoBinding: ActivityMyInfoBinding,val alertDialog: AlertDialog) {

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

    //유저 삭제 함수
    fun userDelete(
        updateUserNo: String
    ) {
        retrofit.create(MyInfoService::class.java).isUserDelect(
            userNo = updateUserNo
        ).enqueue(object :
            Callback<MyInfoVO> {
            override fun onResponse(call: Call<MyInfoVO>, response: Response<MyInfoVO>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var myinfoUserIdCheck = response.body()!!
                    // true 일 경우는 중복된 아이디가 있음, false 일때는 중복된 아이디가 없음.
                    if(myinfoUserIdCheck.message == "true"){
                        Log.d("myinfo userDelete : ", "myinfo userDelete 성공: " + myinfoUserIdCheck.toString())
                        successDialog("회원탈퇴 되었습니다. \n 로그인 화면으로 돌아갑니다.", "확인", "userDelete")
                    }else{
                        Log.d("myinfo userDelete : ", "myinfo userDelete 실패: " + myinfoUserIdCheck.toString())
                        errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
                    }
                } else {
                    Log.d("myinfo userDelete : ", "myinfo userDelete 실패")
                    errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
                }
            }

            override fun onFailure(call: Call<MyInfoVO>, t: Throwable) {
                Log.d("myinfo userDelete : ", "myinfo userDelete 실패")
                errorDialog("문제가 발생하였습니다. \n 다시 시도 또는 관리자에게 문의하세요")
            }
        })
    }

    //유저 업데이트 함수
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
                        successDialog("정보가 수정되었습니다. \n 이전 화면으로 돌아갑니다.", "확인", "userUpdate")

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
            .setCancelable(false)
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
    fun successDialog(context: String, button:String, type:String){

        val successDialogView : View = layoutInflater.inflate(R.layout.pop_format_pw_ok, null)
        val successAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(successDialogView)
            .create()

        val successBinding: PopFormatPwOkBinding by lazy {
            PopFormatPwOkBinding.bind(successDialogView)
        }

        successBinding.tvContent.text = context
        successBinding.btnPwFormatOk.text = button

        successAlertDialog.show()

        successBinding.btnPwFormatOk.setOnClickListener {
            if(type == "userDelete"){
                successAlertDialog.dismiss()

                //내부 저장소 데이터 삭제
                val sharedPreference = getSharedPreferences("user", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sharedPreference.edit()
                editor.remove("userNo")
                editor.commit()
                finishAffinity()

                //로그인화면 실행
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }else{
                successAlertDialog.dismiss()
                finish()
            }
        }
    }

    //회원탈퇴 팝업
    fun deleteDialog(userNo : String){

        val deleteDialogView : View = layoutInflater.inflate(R.layout.pop_user_delete, null)
        val deleteAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(deleteDialogView)
            .create()

        val deleteBinding: PopUserDeleteBinding by lazy {
            PopUserDeleteBinding.bind(deleteDialogView)
        }

        deleteAlertDialog.show()

        //아니요버튼
        deleteBinding.btnNo.setOnClickListener {
            deleteAlertDialog.dismiss()
        }
        
        //네 버튼
        deleteBinding.btnOk.setOnClickListener {
            userDelete(userNo)
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
            addJavascriptInterface(AndroidBridge(myInfoBinding, alertDialog), "avcmApp")
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