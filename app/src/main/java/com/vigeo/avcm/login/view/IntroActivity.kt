package com.vigeo.avcm.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.vigeo.avcm.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val introBinding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(introBinding.root)


        //기기에 저장된 사용자 정보 중 인트로 완수 여부 확인 (리턴 타입: 불리언)
        //기기에 사용자 정보 없을 시엔 인트로 및 로그인 화면 연결
        val isSuccessful = false;

        if(isSuccessful){ //인트로 완수 시

            //로그인 화면 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }else { //인트로 미완수 시

            // 1) ViewPager2 참조
            val viewPager: ViewPager2 = introBinding.ivIntroViewPager

            // 2) FragmentStateAdapter 생성 : Fragment 여러개를 ViewPager2에 연결해주는 역할
            val intropagerFragmentAdapter = IntropagerFragmentAdapter(this)

            // 3) ViewPager2의 adapter에 설정
            viewPager.adapter = intropagerFragmentAdapter

           // Toast.makeText(this, viewPager.currentItem, Toast.LENGTH_SHORT).show()
          //  viewPager.currentItem

        }
    }

}
