package com.vigeo.avcm.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.vigeo.avcm.databinding.ActivityIntroBinding
import com.vigeo.avcm.databinding.ActivityMainBinding
import com.vigeo.avcm.databinding.FragmentIntroStep1Binding

class IntroActivity : AppCompatActivity() {

    private val introBinding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val introStep1Binding: FragmentIntroStep1Binding by lazy {
        FragmentIntroStep1Binding.inflate(layoutInflater)
    }


    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(introBinding.root)

        //인트로 완수 여부 N
        // 1) ViewPager2 참조
        val viewPager : ViewPager2 = introBinding.ivIntroViewPager

        // 2) FragmentStateAdapter 생성 : Fragment 여러개를 ViewPager2에 연결해주는 역할
        val intropagerFragmentAdapter = IntropagerFragmentAdapter(this)

        // 3) ViewPager2의 adapter에 설정
        viewPager.adapter = intropagerFragmentAdapter


        //인트로 완수 여부 Y
        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
        //finish()
    }

}
