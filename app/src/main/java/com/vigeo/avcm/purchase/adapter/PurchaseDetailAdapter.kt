package com.vigeo.avcm.purchase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vigeo.avcm.databinding.ItemPurchaseDetailBinding
import com.vigeo.avcm.databinding.ItemPurchaseListBinding
import com.vigeo.avcm.purchase.data.model.Purchase
import com.vigeo.avcm.purchase.view.PurchaseActivity

class PurchaseDetailAdapter : ListAdapter<Purchase, PurchaseDetailAdapterViewHolder>(PurchaseDiffCallback) {
    var num = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PurchaseDetailAdapterViewHolder{
        return PurchaseDetailAdapterViewHolder(
            ItemPurchaseDetailBinding.inflate(LayoutInflater.from(parent.context)),
        )
    }

    override fun onBindViewHolder(holder: PurchaseDetailAdapterViewHolder, position: Int) {
        val purchase = currentList[position]
        holder.bind(purchase)
    }

    override fun onBindViewHolder(
        holder: PurchaseDetailAdapterViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        num = holder.numGet()
    }

    companion object {

        private val PurchaseDiffCallback = object : DiffUtil.ItemCallback<Purchase>() {
            override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem.prodNo == newItem.prodNo
            }

            override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem == newItem
            }
        }
    }
}