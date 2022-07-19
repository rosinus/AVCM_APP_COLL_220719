package com.vigeo.avcm.collect.view.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityCollectNumBinding
import com.vigeo.avcm.databinding.ActivityMainBinding
import com.vigeo.avcm.data.MySharedPreferences.Companion.pref

class CollectNumActivity : AppCompatActivity() {
    private val binding: ActivityCollectNumBinding by lazy {
        ActivityCollectNumBinding.inflate(layoutInflater)
    }
    /*20220714 작성자 김형창*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //단순 구글 지도 액티비티 - > 다음 액티비이로 넘어와서 프래그먼트로 화면을 뿌려주기 위한 액티비티 단.
        val sharedPreference = getSharedPreferences("vigeo", Context.MODE_PRIVATE)
        var context = applicationContext
        //구글 지도 화면인 CollectActivity 에서 값을 받아와서 프래그먼트 단으로 데이터를 넘기는 작업
        // fragmentCollect1은 CollectSelectFragment를 상속 받음.
        var fragmentCollect1 :CollectSelectFragment = CollectSelectFragment()
        //CollectActivity 에서 키 값을 받아옵니다 . int 타입은 getInt, String 타입은 getString
        var number1 = intent.getStringExtra("address")
        var lat  = intent.getDoubleExtra("lat",0.00)
        var lot  = intent.getDoubleExtra("lot",0.00)
        var userNo : String = pref.getString("userNo", "")

        //btndle을 만들어서 CollectActivity 에서 받아온 데이터를 가져와 다시 bundle 타입에 putStirng 타입으로 담아줍니다.
        var bundle = Bundle()
        bundle.putString("number1", number1)
        bundle.putDouble("lat",lat)
        bundle.putDouble("lot",lot)
        bundle.putString("userNo", userNo)
        ///fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
        fragmentCollect1.arguments = bundle

        //이제 frament2를 보내면 다음 프래그먼트에서 number1 키로 부르면 벨류가 받아와집니다 ^^
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentCollect1).commit()
        }
    }
}
