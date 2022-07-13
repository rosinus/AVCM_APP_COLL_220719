package com.vigeo.avcm.purchase.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityPurchaseBinding
import java.text.DecimalFormat


class PurchaseActivity : AppCompatActivity() {
    private val binding: ActivityPurchaseBinding by lazy {
        ActivityPurchaseBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //가격 단위 포맷
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
        
        binding.tvPrev.setOnClickListener {
            Log.d("클릭", "구매신청 으로 이동")
            finish()
        }

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
                val myToast = Toast.makeText(this.applicationContext, "최대 수량", Toast.LENGTH_SHORT)
                myToast.show()
            }else{
                Log.d("이벤트 감지", "성공")
                Log.d("이벤트 감지", cnt.toString())
                Log.d("이벤트 감지", realCnt.toString())


                val myToast = Toast.makeText(this.applicationContext, "수량 초과", Toast.LENGTH_SHORT)
                myToast.show()

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