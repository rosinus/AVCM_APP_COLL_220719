package com.vigeo.avcm.purchase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vigeo.avcm.databinding.ItemPurchaseListBinding
import com.vigeo.avcm.purchase.data.model.Purchase

class PurchaseAdapter : ListAdapter<Purchase, PurchaseAdapterViewHolder>(PurchaseDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PurchaseAdapterViewHolder{
        return PurchaseAdapterViewHolder(
            ItemPurchaseListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PurchaseAdapterViewHolder, position: Int) {
        val purchase = currentList[position]
        holder.bind(purchase)
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