package com.vigeo.avcm.purchase.adapter

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vigeo.avcm.databinding.ItemPurchaseListBinding
import com.vigeo.avcm.purchase.data.model.Purchase
import com.vigeo.avcm.purchase.view.PurchaseActivity
import java.text.DecimalFormat

class PurchaseAdapterViewHolder (
    private val binding : ItemPurchaseListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(purchase : Purchase){
        var prodNm = purchase.prodNm.toString().removeSurrounding("[", "]")
        var prodWidth = purchase.prodWidth.toString()
        var prodLength = purchase.prodLength.toString()
        var prodThickness = purchase.prodThickness.toString()
        var prodPrice = purchase.prodPrice.toString().toInt()
        var prodNo = purchase.prodNo.toString()

        val dec = DecimalFormat("#,###원")

        itemView.apply {
            binding.ivArticleImage.load(purchase.prodImg)
            binding.tvTitle.text = prodNm
            binding.tvAuthor.text = "$prodWidth cm / $prodLength m / $prodThickness mm"
            binding.tvDatetime.text = dec.format(prodPrice)
            binding.detailBtn.setOnClickListener(){
                Log.d("버튼 이름",prodNo)
                Intent(context, PurchaseActivity::class.java).apply {
                    putExtra("prodNo", prodNo)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}