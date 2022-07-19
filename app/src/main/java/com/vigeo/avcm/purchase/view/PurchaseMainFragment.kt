package com.vigeo.avcm.purchase.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.FragmentPurchaseMainBinding
import com.vigeo.avcm.databinding.FragmentPurchaseOkBinding
import com.vigeo.avcm.databinding.PopFormatBuyNoBinding
import com.vigeo.avcm.databinding.PopFormatBuyOkBinding
import com.vigeo.avcm.purchase.adapter.PurchaseDetailAdapter
import com.vigeo.avcm.purchase.data.model.SaveVo
import com.vigeo.avcm.purchase.data.model.Service.PurchaseSaveService
import com.vigeo.avcm.purchase.viewmodel.PurchaseViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat


class PurchaseMainFragment : Fragment() {

    private var _binding : FragmentPurchaseMainBinding? = null
    private val binding : FragmentPurchaseMainBinding get() = _binding!!

    lateinit var purchaseViewModel: PurchaseViewModel
    lateinit var purchaseDetailAdapter: PurchaseDetailAdapter

    var prodNm : String = ""
    var prodLength : String = ""
    var prodThickness : String = ""
    var prodWidth : String = ""
    var prodPrice : String = ""
    var prodCmpUserNo : String = ""
    var prodCnt : String = ""
    var manufacturer : String = ""
    var userNm : String = ""
    var prodLtdCnt : String = ""
    var prodLtdCnt2 : String = ""
    var buy : String = ""
    var tel : String = ""
    var sellCnt : String = ""
    var cnt : Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseMainBinding.inflate(inflater, container, false)

        purchaseViewModel = (activity as PurchaseActivity).purchaseViewModel

        var prodNo = (activity as PurchaseActivity).prodNo

        setupRecyclerView()

        purchaseViewModel.resultDetail.observe(viewLifecycleOwner){ response ->
            val purchase = response.purchaseList
            val dec = DecimalFormat("#,###원")
            prodNm          = purchase[0].prodNm.toString().removeSurrounding("[", "]")
            prodLength      = purchase[0].prodLength.toString()+ "m"
            prodThickness   = purchase[0].prodThickness.toString() + "mm"
            prodWidth       = purchase[0].prodWidth.toString() + "cm"
            prodPrice       = dec.format(purchase[0].prodPrice.toString().toInt()).toString()
            prodCmpUserNo   = purchase[0].prodCmpUserNo.toString()
            prodCnt         = purchase[0].prodCnt.toString()
            tel             = purchase[0].phoneNum.toString()
            manufacturer    = purchase[0].manufacturer.toString()
            prodLtdCnt      = dec.format(purchase[0].prodPrice.toString().toInt()).toString()
            prodLtdCnt2     = purchase[0].prodLtdCnt.toString()
            binding.buy.text = prodLtdCnt
            userNm          = purchase[0].userNm.toString()
            sellCnt         = purchase[0].sellCnt.toString()


            cnt = (purchase[0].prodLtdCnt.toString().toInt()) - (purchase[0].sellCnt.toString().toInt())

            binding.prodNm.text = prodNm
            binding.manufacturer.text = manufacturer
            binding.prodLength.text = prodLength
            binding.pordWidth.text = prodWidth
            binding.prodThickness.text = prodThickness
            binding.userNm.text = userNm
            binding.prodPirce.text = prodPrice
            binding.prodLtdCnt2.text = prodLtdCnt2
            binding.buy.text = buy
            binding.prodLtdCnt.text = cnt.toString()
            binding.prodLtdCnt2.text = prodLtdCnt
            binding.prodImg.load(purchase[0].prodImg)

            purchaseDetailAdapter.submitList(purchase)
        }

        binding.tvPrev.setOnClickListener{

        }

        binding.etPhoneNum.setOnClickListener{

            Intent(context, PurchaseActivity::class.java).apply {
                Intent(Intent.ACTION_DIAL, Uri.parse(tel))
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { context?.startActivity(this) }

        }

        binding.prodCnt.setOnEditorActionListener{ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                handled = true
                Log.d("수량",binding.prodCnt.text.toString())
            }
            numFormat(true)
            binding.prodCnt.setEnabled(false)
            binding.prodCnt.setEnabled(true)
            handled

        }

        binding.mi.setOnClickListener {
            Log.d("클릭","마이너스 클릭")
            numFormat(false)
        }

        binding.pl.setOnClickListener {
            Log.d("클릭","플러스 클릭")
            numFormat(true)
        }
        purchaseViewModel.purchaseDetail(prodNo)

        binding.tvPrev.setOnClickListener {
            Log.d("클릭", "구매신청 으로 이동")
        }
        binding.btnOk.setOnClickListener(){
            purchaseDialog()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        purchaseDetailAdapter = PurchaseDetailAdapter()
        binding.rvSearchResult2.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL

                )
            )
            adapter = purchaseDetailAdapter
        }
    }
    fun purchaseDialog(){
        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val purchaseDialogView : View = layoutInflater.inflate(R.layout.fragment_purchase_ok, null)
        val purchaseAlertDialog : AlertDialog = AlertDialog.Builder(activity as PurchaseActivity)
            .setView(purchaseDialogView)
            .create()
        purchaseAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val purchaseBinding: FragmentPurchaseOkBinding by lazy {
            FragmentPurchaseOkBinding.bind(purchaseDialogView)
        }
        val dec = DecimalFormat("#,###원")

        purchaseBinding.prodNm2.text = prodNm
        purchaseBinding.pordWidth2.text = prodWidth
        purchaseBinding.prodThickness2.text = prodThickness
        purchaseBinding.prodLength2.text = prodLength
        purchaseBinding.prodPirce3.text = prodPrice
        purchaseBinding.prodPirce4.text = cnt.toString()

        purchaseBinding.buy4.text = buy

        purchaseBinding.btnNo.setOnClickListener{
            println("hi")
            val gson : Gson = gsonCreate()
            val retrofit = retrofitBuild(gson)
            purchaseSave(retrofit)
            purchaseAlertDialog.cancel()
        }
        purchaseBinding.btnOk.setOnClickListener{
            purchaseAlertDialog.cancel()
        }
    }

    //구매 팝업
    fun purchaseOkDialog(){
        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val purchaseDialogView : View = layoutInflater.inflate(R.layout.pop_format_buy_ok, null)
        val purchaseAlertDialog : AlertDialog = AlertDialog.Builder(activity as PurchaseActivity)
            .setView(purchaseDialogView)
            .create()
        purchaseAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val purchaseOkBinding: PopFormatBuyOkBinding by lazy {
            PopFormatBuyOkBinding.bind(purchaseDialogView)
        }

        purchaseOkBinding.btnBuyFormatOk.setOnClickListener{
            purchaseAlertDialog.cancel()
        }
    }

    //구매 팝업
    fun purchaseNoDialog(){
        //계정 정보 없음 팝업을 현재 레이아웃 위에 다이얼로그로 생성
        val purchaseDialogView : View = layoutInflater.inflate(R.layout.pop_format_buy_no, null)
        val purchaseAlertDialog : AlertDialog = AlertDialog.Builder(activity as PurchaseActivity)
            .setView(purchaseDialogView)
            .create()
        purchaseAlertDialog.show()

        //다이얼로그로 뷰바인딩
        //주의점: 팝업 생기기 전에 뷰바인딩 하면 화면만 불러오고 데이터 불러올 수 없음
        val purchaseOkBinding: PopFormatBuyNoBinding by lazy {
            PopFormatBuyNoBinding.bind(purchaseDialogView)
        }

        purchaseOkBinding.btnBuyFormatNo.setOnClickListener{
            purchaseAlertDialog.cancel()
        }
    }

    private fun purchaseSave(retrofit: Retrofit) {
        retrofit.create(PurchaseSaveService::class.java).purchaseSave(
            vigeoToken = "O304UIUw3P78ZZPC5qBkmQ==",
            prodNo = "1",
            consumerNo = "42",
            prodCmpnyNo = prodCmpUserNo,
            buyCnt = "1"
        ).enqueue(object :
            Callback<SaveVo> {
            override fun onResponse(call: Call<SaveVo>, response: Response<SaveVo>) {

                var saveVo : SaveVo = response.body()!!
                val result = saveVo.message

                if(response.isSuccessful){
                    if(result){ //성공적으로 추가됨
                        //회원가입 완료 팝업 호출
                        return purchaseOkDialog()
                    }else{
                        //오류 팝업 호출
                        return purchaseNoDialog()
                    }
                    // 정상적으로 통신이 성공된 경우
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"실패: ", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<SaveVo>, t: Throwable) {
                Log.d("실패: ", "onResponse 실패")
            }
        })
    }

    private fun gsonCreate() : Gson {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        return gson
    }

    private fun retrofitBuild(gson : Gson) : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.181:8080/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

    fun numFormat(flag : Boolean) : Boolean{

        var cnt : Int = binding.prodCnt.text.toString().toInt()
        var pri : String = binding.prodPirce.text.toString()
        var realCnt : Int = binding.prodLtdCnt.text.toString().toInt()

        var flag = flag

        if(flag){
            if(realCnt > cnt) {
                cnt++
            }else if(realCnt == cnt){

            }else{
                Log.d("이벤트 감지", "성공")
                Log.d("이벤트 감지", cnt.toString())
                Log.d("이벤트 감지", realCnt.toString())

                pri = pri.replace(",","").replace("원","")
                var price : Int = pri.toInt()

                val dec = DecimalFormat("#,###원")

                var result : Int = realCnt * price

                binding.prodCnt.setText(realCnt.toString())
                binding.buy.text = dec.format(result)

                return false
            }

        }else{
            if(cnt < 1){
                cnt = 0
            }else{
                cnt--
            }
        }

        pri = pri.replace(",","").replace("원","")
        var price : Int = pri.toInt()

        val dec = DecimalFormat("#,###원")

        var result : Int = cnt * price

        binding.prodCnt.setText(cnt.toString())
        binding.buy.text = dec.format(result)

        return true
    }

}