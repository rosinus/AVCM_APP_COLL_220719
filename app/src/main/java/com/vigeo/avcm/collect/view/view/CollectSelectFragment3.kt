package com.vigeo.avcm.collect.view.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.collect.view.viewmodel.CollectModel
import com.vigeo.avcm.collect.view.viewmodel.CollectService
import com.vigeo.avcm.databinding.FragmentCollect3Binding
import com.vigeo.avcm.main.view.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CollectSelectFragment3 : Fragment() {
    private var _binding : FragmentCollect3Binding? = null
    private val binding : FragmentCollect3Binding get() = _binding!!

    var count = 0
    //호출 했을때

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //Activity에 의존 프레그먼트를 안고 있는 액티비에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // 뷰가 생성 되었을 때 (화면과 연결)
    // 프레그먼트와 레이아웃을 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentCollect3Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //마지막으로 데이터를 받고 여기서 플러스 마이너스 한 값까지 담아서 팝업에서 insert처리 합니다
        var mylocation = arguments?.getString("mylocation")
        var previousCount = arguments?.getString("count3")
        var previousCount2 = arguments?.getString("count12")
        var lat = arguments?.getDouble("lat")
        var lot = arguments?.getDouble("lot")
        var userNo = arguments?.getInt("userNo")
        //구글맵 주소 , 3중 필름 ,12중 필름,위도및경도 데이터 다 받아왔습니다.

        super.onViewCreated(view, savedInstanceState)


        binding.btnMinus.setOnClickListener {
            if(count < 1){
                count = 0
            }else {
                count --
                binding.filmStepEnd.setText(count.toString())
            }
        }

        binding.btnPlus.setOnClickListener {
            count ++
            binding.filmStepEnd.setText(count.toString())
        }

        binding.btnNext.setOnClickListener {

           val dialogView = layoutInflater.inflate(R.layout.pop_collect, null)
           var alertDialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create()
            val vinyl3 = dialogView.findViewById<TextView>(R.id.vinyl3).setText(previousCount+"m")
            val vinyl12 = dialogView.findViewById<TextView>(R.id.vinyl12).setText(previousCount2+"m")
            val vinyl1 = dialogView.findViewById<TextView>(R.id.vinyl1).setText(count.toString()+"m")
                alertDialog.show()
           var btnNo : Button? =  alertDialog.findViewById(R.id.btn_cancell)
           var btnYes : Button? = alertDialog.findViewById(R.id.btn_ok)


            btnNo?.setOnClickListener {
                alertDialog.dismiss()
            }

            btnYes?.setOnClickListener {
                val gson : Gson = GsonBuilder()
                .setLenient()
                .create()
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.189:8080/")
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                val prodInfoLength3 = binding.filmStepEnd.text.toString().toDouble()
                Log.d("데이터전송","ㄱ?"+prodInfoLength3)
                val prodInfoLength1 = previousCount?.toDouble()
                val prodInfoLength2 = previousCount2?.toDouble()
                val applicantNo = userNo
                val collectAddr = mylocation
                val collectGpsLen = lat
                val collectGpsLon = lot

                retrofit.create(CollectService::class.java).collectionSignup(
                    "O304UIUw3P78ZZPC5qBkmQ==",
                    prodInfoLength1 = prodInfoLength1,
                    prodInfoLength2 = prodInfoLength2,
                    prodInfoLength3 = prodInfoLength3,
                    applicantNo = applicantNo!!,
                    collectAddr = collectAddr,
                    collectGpsLen = collectGpsLen!!,
                    collectGpsLon = collectGpsLon!!
                ).enqueue(object :
                    Callback<CollectModel> {

                    override fun onResponse(call: Call<CollectModel>, response: Response<CollectModel>) {
                        if(response.isSuccessful){
                            // 정상적으로 통신이 성공된 경우
                            Log.d("\"수거 : ", "신청 성공:" +response.body().toString());
                            alertDialog.dismiss()
                            val dialogView = layoutInflater.inflate(R.layout.pop_collect_ok, null)
                            var alertDialog1 = AlertDialog.Builder(requireContext())
                                .setView(dialogView)
                                .setCancelable(false)
                                .create()
                            alertDialog1.show()
                            var btnYes : Button? = alertDialog1.findViewById(R.id.btn_success)

                            btnYes?.setOnClickListener {
                                alertDialog1.dismiss()
                                val intent = Intent(getActivity(), MainActivity::class.java)
                                startActivity(intent)
                            }
                        }else{
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("\"수거 : ", "신청 실패")
                        }
                    }
                    override fun onFailure(call: Call<CollectModel>, t: Throwable) {
                        Log.d("수거 : ", "신청 실패")
                    }
                })
            }
        }

        binding.btnBack.setOnClickListener {
            var fragmentBack : CollectSelectFragment2 = CollectSelectFragment2()
            var bundle = Bundle()
            bundle.putString("backmylocation", mylocation.toString())
            bundle.putString("backcount", previousCount.toString())
            bundle.putInt("backUserNo", userNo!!)
            fragmentBack.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_collect, fragmentBack).commit()
        }
    }
}