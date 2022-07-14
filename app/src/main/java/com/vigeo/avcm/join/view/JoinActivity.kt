package com.vigeo.avcm.join.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityJoinBinding
import com.vigeo.avcm.databinding.PopJoinOkBinding
import com.vigeo.avcm.login.view.LoginActivity

class JoinActivity : AppCompatActivity() {

    private val joinBinding: ActivityJoinBinding by lazy {
        ActivityJoinBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(joinBinding.root)

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

            //보낼 파라미터 값
            val phNum = joinBinding.etLoginPhoneNum.text    //연락처
            val name = joinBinding.etLoginName.text         //성명
            val postNum = joinBinding.etLoginPostNum.text   //우편번호
            val addr = joinBinding.etLoginAddr.text         //주소
            val detailAddr = joinBinding.etLoginDaddr.text  //상세주소
            val userGb = "04" //농민

            //사용자 정보 등록 API 호출 (리턴타입: 불리언)
            val isCompeleteJoin = true;

            if(isCompeleteJoin){

                //가입 완료 팝업 호출
                joinOkDialog()


            }else{

            }
        }

    }

    //가입 완료 시 팝업
    fun joinOkDialog(){

        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val joinOkDialogView : View = layoutInflater.inflate(R.layout.pop_join_ok, null)
        val joinOkAlertDialog : AlertDialog = AlertDialog.Builder(this)
            .setView(joinOkDialogView)
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
            startActivity(intent)
            finish()
        }
    }



}