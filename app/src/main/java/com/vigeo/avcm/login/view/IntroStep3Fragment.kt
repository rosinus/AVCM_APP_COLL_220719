package com.vigeo.avcm.login.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vigeo.avcm.databinding.FragmentIntroStep3Binding

class IntroStep3Fragment : Fragment() {

    private val fragmentIntroStep3Binding: FragmentIntroStep3Binding by lazy {
        FragmentIntroStep3Binding.inflate(layoutInflater)
    }

    companion object{
        //log 출력을 편하게 하기 위해서
        const val TAG : String = "로그"

        fun newInstance() : IntroStep3Fragment {
            return IntroStep3Fragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentIntroStep3Binding.btnIntroOk.setOnClickListener{

            //사용자 인트로 봤음 처리 체크

            val intent = Intent(activity, LoginActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            //화면 없애야함 finish()
        }
        return fragmentIntroStep3Binding.root
       //return inflater.inflate(R.layout.fragment_intro_step3, container, false)
    }
}