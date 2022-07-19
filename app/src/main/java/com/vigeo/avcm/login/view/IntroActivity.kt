package com.vigeo.avcm.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.vigeo.avcm.data.MySharedPreferences.Companion.pref
import com.vigeo.avcm.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val introBinding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(introBinding.root)

        //기기에 저장된 정보가 있을 시
        if(pref.getString("userNo", "") != ""){

            //내부 저장소에서 isIntro 값 호출, 값이 없을 경우 false
            val isIntro = pref.getBoolean("isIntro", false)

            if(isIntro){ //인트로 완수 했을 시

                //로그인 화면 이동
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }else{ //완수 안 했을 시

                val viewPager: ViewPager2 = introBinding.ivIntroViewPager
                val intropagerFragmentAdapter = IntropagerFragmentAdapter(this)
                viewPager.adapter = intropagerFragmentAdapter

            }

        }else{ //저장된 정보가 없을 시 인트로 노출

            val isIntro = pref.getBoolean("isIntro", false)

            if(isIntro){ //사용자 정보는 없으나 인트로 이력 있을 경우

                //로그인 화면 이동
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()


            }else { //모두 없을 경우

                // 1) ViewPager2 참조
                val viewPager: ViewPager2 = introBinding.ivIntroViewPager

                // 2) FragmentStateAdapter 생성 : Fragment 여러개를 ViewPager2에 연결해주는 역할
                val intropagerFragmentAdapter = IntropagerFragmentAdapter(this)

                // 3) ViewPager2의 adapter에 설정
                viewPager.adapter = intropagerFragmentAdapter
            }
        }
    }

}
