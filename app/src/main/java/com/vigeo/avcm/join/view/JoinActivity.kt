package com.vigeo.avcm.join.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private val joinBinding: ActivityJoinBinding by lazy {
        ActivityJoinBinding.inflate(layoutInflater)
    }



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(joinBinding.root)

/*
        //인트로 완수 여부 N
        // 1) ViewPager2 참조
        val viewPager : ViewPager2 = joinBinding.navHostFragmentContainer

        // 2) FragmentStateAdapter 생성 : Fragment 여러개를 ViewPager2에 연결해주는 역할
        val intropagerFragmentAdapter = IntropagerFragmentAdapter(this)

        // 3) ViewPager2의 adapter에 설정
        viewPager.adapter = intropagerFragmentAdapter

*/

        //인트로 완수 여부 Y
        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}