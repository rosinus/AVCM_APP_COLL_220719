package com.vigeo.avcm.main.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.collect.view.view.CollectActivity
import com.vigeo.avcm.databinding.ActivityMainBinding
import com.vigeo.avcm.main.viewmodel.KakaoMapMarker
import com.vigeo.avcm.myInfo.view.MyInfoFragment
import com.vigeo.avcm.purchase.repository.PurchaseRepositoryImpl
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModelProviderFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    private var backKeyPressedTime: Long = 0
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var purchaseViewModel: PurchaseViewModel



    companion object {
        const val TAG: String = "로그"
    }
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    //메모리에 올라 갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        onBottomNavItemListener()
        if (savedInstanceState == null) {
            binding.bottomNavi.selectedItemId = R.id.menu_home
        }
        val purchaseRepository = PurchaseRepositoryImpl()
        val factory = PurchaseViewModelProviderFactory(purchaseRepository)
        purchaseViewModel = ViewModelProvider(this, factory)[PurchaseViewModel::class.java]
    }


    private fun onBottomNavItemListener() {

        binding.bottomNavi.setOnItemSelectedListener {
            //Log.d("클릭")
            when (it.itemId) {
                R.id.menu_home -> {
                    Log.d(TAG, "MainActivity - 홈버튼 클릭")
                    HomeFragment.newInstance()
                    //현재 프래그먼트를 지우고 다음 내가 클릭한 프래그먼트를 가져오기.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, HomeFragment()).commit()
                    true
                }

                R.id.menu_collect -> {
                    Log.d(TAG, "MainActivity - 수거신청 클릭")
                    val intent = Intent(this, CollectActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_purchase -> {
                    Log.d(TAG, "MainActivity - 구매신청 클릭")
                    //PurchaseFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, PurchaseFragment()).commit()
                    true
                }
                R.id.menu_page -> {
                    Log.d(TAG, "MainActivity - 내정보 클릭")
                    MyPageFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, MyInfoFragment()).commit()
                    true
                }
                else -> false
            }

        }
    }

    fun getHashKey() {
        var packageInfo: PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures) {
            try {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
           Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
        }
    }
}
