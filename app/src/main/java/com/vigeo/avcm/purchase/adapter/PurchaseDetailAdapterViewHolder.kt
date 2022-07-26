package com.vigeo.avcm.purchase.adapter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.vigeo.avcm.databinding.ItemPurchaseDetailBinding
import com.vigeo.avcm.purchase.data.model.Purchase
import com.vigeo.avcm.purchase.view.PurchaseActivity
import java.text.DecimalFormat


class PurchaseDetailAdapterViewHolder (
    private val binding : ItemPurchaseDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    var num : Int = 0
    fun bind(purchase: Purchase){
        itemView.apply {
            val bundle = Bundle()
            bundle.putString("title", num.toString())


            val dec = DecimalFormat("#,###원")

            Log.d("뷰홀더",purchase.prodNm.toString().removeSurrounding("[", "]"))
            binding.prodNm.text = purchase.prodNm.toString().removeSurrounding("[", "]")
            binding.manufacturer.text = purchase.manufacturer.toString().removeSurrounding("[", "]")
            binding.prodLength.text = purchase.prodLength.toString() + "m"
            binding.pordWidth.text = purchase.prodWidth.toString() + "cm"
            binding.prodThickness.text = purchase.prodThickness.toString() + "mm"
            binding.userNm.text = purchase.userNm.toString()
            binding.prodPirce.text = dec.format(purchase.prodPrice.toString().toInt()).toString()
            binding.prodLtdCnt2.text = purchase.prodLtdCnt.toString()
            binding.buy.text = dec.format(purchase.prodPrice.toString().toInt()).toString()

            var cnt = (purchase.prodLtdCnt.toString().toInt()) - (purchase.sellCnt.toString().toInt())

            binding.prodLtdCnt.text = cnt.toString()
            binding.etPhoneNum.setOnClickListener{
                var tel : String = purchase.phoneNum.toString()

                Intent(context, PurchaseActivity::class.java).apply {
                    Intent(Intent.ACTION_DIAL, Uri.parse(tel))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }

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

        num = cnt
        println(num)
        return true
    }

    fun numGet() : Int{
        return num
    }
}