package com.vigeo.avcm.login.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntropagerFragmentAdapter(fragmentActivity: IntroActivity): FragmentStateAdapter(fragmentActivity) {

    /**
     * 출처 및 자세한 사용법 원할 시
     * https://curryyou.tistory.com/415
     * */


    // 1. ViewPager2에 연결할 Fragment 들을 생성
    val fragmentList = listOf<Fragment>(IntroStep1Fragment(), IntroStep2Fragment(), IntroStep3Fragment())

    // 2. ViesPager2에서 노출시킬 Fragment 의 갯수 설정
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // 3. ViewPager2의 각 페이지에서 노출할 Fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}