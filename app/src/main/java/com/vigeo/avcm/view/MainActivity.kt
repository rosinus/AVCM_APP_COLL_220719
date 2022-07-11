package com.vigeo.avcm.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityMainBinding
import com.vigeo.avcm.viewmodel.KakaoMapMarker
import com.vigeo.avcm.viewmodel.KakaoMapMarkerProviderFactory
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var kakaoMapMarker: KakaoMapMarker

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

        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var mapView = MapView(this)

        val factory = KakaoMapMarkerProviderFactory(mapView,lm)
        kakaoMapMarker = ViewModelProvider(this, factory)[KakaoMapMarker::class.java]

        onBottomNavItemListener()
        if (savedInstanceState == null) {
            binding.bottomNavi.selectedItemId = R.id.menu_home
        }

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
                    CollectFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, CollectFragment()).commit()
                    true
                }
                R.id.menu_purchase -> {
                    Log.d(TAG, "MainActivity - 구매신청 클릭")
                    PurchaseFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, PurchaseFragment()).commit()
                    true
                }
                R.id.menu_page -> {
                    Log.d(TAG, "MainActivity - 내정보 클릭")
                    MyPageFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_frame, MyPageFragment()).commit()
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
}
