package com.vigeo.avcm.view

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.vigeo.avcm.R
import com.vigeo.avcm.data.model.User
import com.vigeo.avcm.data.repository.retrofit.RetrofitBuilder
import com.vigeo.avcm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_collect.*
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

class MainActivity : AppCompatActivity(){

    private lateinit var homeFragment: HomeFragment
    private lateinit var collectFragment: CollectFragment
    private lateinit var myPageFragment: MyPageFragment
    private lateinit var purchaseFragment: PurchaseFragment
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    companion object{

        const val TAG: String ="로그"
    }

    //메모리에 올라 갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 레이아웃과 연결
        setContentView(R.layout.activity_main)


        try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            val md = MessageDigest.getInstance("SHA")
            for (signature in signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                var hashcode = String(Base64.encode(md.digest(), 0))
                Log.d("hashcode", "" + hashcode)
            }
        } catch (e: Exception) {
            Log.d("hashcode", "에러::" + e.toString())

        }
        Log.d(TAG, "MainActivity - onCreate() called")
        bottomNavi.setOnNavigationItemSelectedListener(onBottomNavItemListener)

        homeFragment = HomeFragment.newInstance()
        //add는 처음 프레그먼트 생성 replace는 현재 프레그먼트를 지우고 택한걸로 교체.

        supportFragmentManager.beginTransaction().add(R.id.fragment_frame, homeFragment).commit()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.d("FCM", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        var service = RetrofitBuilder().service

        service.getUserPage("1")?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성공된 경우
                    var result: User? = response.body()
                    Log.d("YMC", "onResponse 성공: " + result?.toString());
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }


   private val onBottomNavItemListener = BottomNavigationView.OnNavigationItemSelectedListener {
       when(it.itemId) {
           R.id.menu_home -> {
               Log.d(TAG, "MainActivity - 홈버튼 클릭")
               homeFragment = HomeFragment.newInstance()
               //현재 프래그먼트를 지우고 다음 내가 클릭한 프래그먼트를 가져오기.

               supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, homeFragment).commit()
           }
           R.id.menu_collect -> {
               Log.d(TAG, "MainActivity - 수거신청 클릭")
               collectFragment = CollectFragment.newInstance()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, collectFragment).commit()
           }
           R.id.menu_purchase -> {
               Log.d(TAG, "MainActivity - 구매신청 클릭")
               purchaseFragment = PurchaseFragment.newInstance()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, purchaseFragment).commit()
           }
           R.id.menu_page -> {
               Log.d(TAG, "MainActivity - 내정보 클릭")
               myPageFragment = MyPageFragment.newInstance()
               supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, myPageFragment).commit()
           }
       }
           true
       }
   }
