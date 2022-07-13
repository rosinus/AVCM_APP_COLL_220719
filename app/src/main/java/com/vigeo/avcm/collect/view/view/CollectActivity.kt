package com.vigeo.avcm.collect.view.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityCollectBinding
import com.vigeo.avcm.main.view.MainActivity
import java.util.*


class CollectActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding: ActivityCollectBinding by lazy {
        ActivityCollectBinding.inflate(layoutInflater)
    }
    private lateinit var mMap: GoogleMap
    var googleMap: GoogleMap? = null
    var mapFragment: MapFragment? = null
    var locationManager : LocationManager? = null
    var boxMap: RelativeLayout? = null
    var mLatitude : Double? = null
    var mLongitude : Double? = null


    //지오 코더
    var currentLocation: String = "현재 위치"
    var mGeocoder: Geocoder? = null
    var mResultList: List<Address>? = null
    var locationProviderClient: FusedLocationProviderClient? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnClose : Button = findViewById(R.id.btnClose)
        var CollectNext = binding.btnOk
        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("back",btnClose.text.toString())
            startActivity(intent)
        }

        CollectNext.setOnClickListener {
            val intent = Intent(this, CollectNumActivity::class.java)
            intent.putExtra("back",btnClose.text.toString())
            startActivity(intent)
        }


        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        val context = LOCATION_SERVICE
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mMap = googleMap

        val latLng = LatLng(35.818083, 127.107778)
        //구글 맵 내 위치 불러오기 버튼
        mMap.setMyLocationEnabled(true);
        //구글 맵 줌인 줌아웃
        googleMap.uiSettings.isZoomControlsEnabled = true
        //마커 생성
        mMap.addMarker(MarkerOptions().position(latLng).title("마커 제목"))
        //첫 시작 위치를 marker 내 위치로
        val position =
            com.google.android.gms.maps.model.CameraPosition.builder().target(latLng).zoom(18f)
                .build()
        googleMap?.moveCamera((CameraUpdateFactory.newCameraPosition(position)))
        /*mMap.animateCamera(CameraUpdateFactory.zoomTo(13F))*/
        //마커 클릭 이벤트

        //지도 이동시 중심점 위도 경도 가져오기
        googleMap?.setOnCameraIdleListener {
            val position = googleMap!!.cameraPosition
            val zoom = position.zoom
            val latitude = position.target.latitude
            val longitude = position.target.longitude
            Log.d("kkang", "user change : $zoom, $latitude, $longitude")
            mMap.addMarker(MarkerOptions().position(latLng).title("마커 제목"))

            try {
                mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
                locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                var str = binding.myLocation
                locationProviderClient!!.lastLocation.addOnSuccessListener {
                    var latitude = position.target.latitude
                    var longitude = position.target.longitude
                    println("1위도: $latitude, 경도: $longitude")
                    try {
                        mResultList = mGeocoder!!.getFromLocation(
                            latitude!!, longitude!!, 1
                        )
                        println("위치 정보 받아오기 성공")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "좌표를 변환하지 못했습니다.", Toast.LENGTH_LONG)
                            .show()
                    }
                    if (!mResultList?.isEmpty()!!) {
                        currentLocation = mResultList!![0].getAddressLine(0)
                        currentLocation = currentLocation.substring(9)
                        str.text = currentLocation
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "위치 정보를 받아오지 못했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

}
