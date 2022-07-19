package com.vigeo.avcm.purchase.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.vigeo.avcm.databinding.ItemCollectListHistoryBinding
import com.vigeo.avcm.purchase.data.model.Collect
import com.vigeo.avcm.purchase.view.CollectDetailActivity
import com.vigeo.avcm.purchase.view.PurchaseActivity

class CollectAdapterViewHolder (
    private val binding : ItemCollectListHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(collect : Collect){
        itemView.apply {

            var stateGb = collect.collectGb
            var collectNo = collect.collectNo
            collect.collectNo.toString().removeSurrounding("[", "]")
            binding.date.text = collect.collectDate.toString()
            binding.gb01m.text = collect.prodInfoLength1.toString()
            binding.gb02m.text = collect.prodInfoLength2.toString()
            binding.gb03m.text = collect.prodInfoLength3.toString()
            binding.state.text = collect.collectGbNm


            if (stateGb == "01"){
                binding.btn1.text = "취소 하기 >"
            }else if (stateGb == "02"){
                binding.btn1.text = "업체 전화 >"
            }else if (stateGb == "03"){
                binding.btn1.text = ""
            }

            binding.btn.setOnClickListener {
                Log.d("상세보기","클릭")
                Intent(context, CollectDetailActivity::class.java).apply {
                        putExtra("collectNo", collectNo)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }
            }
            binding.btn1.setOnClickListener {
                var flag = binding.btn1.text
                if(flag == "취소 하기 >"){
                    Log.d("취소하기","클릭")
//                    Intent(context, PurchaseActivity::class.java).apply {
//                        putExtra("collectNo", collectNo)
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }.run { context.startActivity(this) }
                }else if(flag == "업체 전화 >"){
                    Log.d("업체전화","클릭")
//                    Intent(context, PurchaseActivity::class.java).apply {
//                        Intent(Intent.ACTION_DIAL, Uri.parse("01023204630"))
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }.run { context.startActivity(this) }
                }
            }
        }
    }
}