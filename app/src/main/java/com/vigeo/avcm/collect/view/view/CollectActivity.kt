package com.vigeo.avcm.collect.view.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.collect.view.viewmodel.*
import com.vigeo.avcm.databinding.ActivityCollectBinding
import com.vigeo.avcm.main.view.MainActivity
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class CollectActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding: ActivityCollectBinding by lazy {
        ActivityCollectBinding.inflate(layoutInflater)
    }
    private lateinit var mMap: GoogleMap
    //지오 코더
    var currentLocation: String = "현재 위치"
    var mGeocoder: Geocoder? = null
    var mResultList: List<Address>? = null
    var locationProviderClient: FusedLocationProviderClient? = null
    var CollectSize: Int? = null
    var len = ArrayList<Double>()
    var lon = ArrayList<Double>()
    var collectGroundNm = ArrayList<String>()
    var lat : Double = 0.00
    var lot : Double = 0.00

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
        var mylocation : TextView = binding.myLocation
        var CollectNext = binding.btnOk

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("back",btnClose.text.toString())
            startActivity(intent)
        }

        CollectNext.setOnClickListener {

            val intent = Intent(this, CollectNumActivity::class.java)
            intent.putExtra("back",btnClose.text.toString())
            intent.putExtra("address",mylocation.getText().toString())
            intent.putExtra("lat",lat.toDouble())
            intent.putExtra("lot",lot.toDouble())
            startActivity(intent)
        }


        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }


    override fun onMapReady(googleMap: GoogleMap) {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.189:8080/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(CollectService::class.java).getGpsList("O304UIUw3P78ZZPC5qBkmQ==").enqueue(object : Callback<CollectModel> {

            override fun onResponse(call: Call<CollectModel>, response: Response<CollectModel>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성공된 경우
                    var Collect  = response.body()!!
                    CollectSize = Collect.collectList.size
                    for(i in 0 .. Collect.collectList.size-1 step(1)){
                        len.add(Collect.collectList.get(i).gpsLen)
                        lon.add(Collect.collectList.get(i).gpsLon)
                        collectGroundNm.add(Collect.collectList.get(i).collectGroundNm)
                    }
                    for(i in 0..Collect.collectList.size-1 step(1)){
                        var latLng = LatLng(len.get(i), lon.get(i))
                        mMap.addMarker(MarkerOptions().position(latLng).title("집하장명:"+collectGroundNm.get(i)))
                    }
                    //첫 시작 위치를 marker 내 위치로
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"집하장 : ", "통신 실패")
                }
            }
            override fun onFailure(call: Call<CollectModel>, t: Throwable) {
                Log.d("집하장 : ", "통신 실패")
            }
        })



        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        //마지막 위치 받아오기
        val loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if(loc_Current != null) {
            var cur_lat = loc_Current!!.latitude //위도
            var cur_lon = loc_Current!!.longitude //경도
            var Lang = LatLng(cur_lat,cur_lon)
            val position =
                com.google.android.gms.maps.model.CameraPosition.builder().target(Lang).zoom(18f)
                    .build()
            googleMap?.moveCamera((CameraUpdateFactory.newCameraPosition(position)))
        }else if(loc_Current == null){
            var cur_lat = 35.8343858//위도
            var cur_lon = 127.1108093 //경도
            var Lang = LatLng(cur_lat,cur_lon)

            val position =
                com.google.android.gms.maps.model.CameraPosition.builder().target(Lang).zoom(18f)
                    .build()
            googleMap?.moveCamera((CameraUpdateFactory.newCameraPosition(position)))
        }


        val context = LOCATION_SERVICE
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mMap = googleMap
        //구글 맵 내 위치 불러오기 버튼
        mMap.setMyLocationEnabled(true);

        //구글 맵 줌인 줌아웃
        googleMap.uiSettings.isZoomControlsEnabled = true
        //지도 이동시 중심점 위도 경도 가져오기
        googleMap?.setOnCameraIdleListener {
            val position = googleMap!!.cameraPosition
            val zoom = position.zoom
            val latitude = position.target.latitude
            val longitude = position.target.longitude

            try {
                mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
                locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                var str = binding.myLocation
                locationProviderClient!!.lastLocation.addOnSuccessListener {
                    var latitude = position.target.latitude
                    var longitude = position.target.longitude

                    lat = latitude
                    lot = longitude
                    try {
                        mResultList = mGeocoder!!.getFromLocation(
                            latitude!!, longitude!!, 1
                        )
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
    override fun onBackPressed() {
        //super.onBackPressed();

    }
}
