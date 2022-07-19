package com.vigeo.avcm.purchase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vigeo.avcm.databinding.ItemCollectListHistoryBinding
import com.vigeo.avcm.purchase.data.model.Collect

class CollectAdapter : ListAdapter<Collect, CollectAdapterViewHolder>(CollectDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CollectAdapterViewHolder{
        return CollectAdapterViewHolder(
            ItemCollectListHistoryBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CollectAdapterViewHolder, position: Int) {
        val collect = currentList[position]
        holder.bind(collect)
    }

    companion object {

        private val CollectDiffCallback = object : DiffUtil.ItemCallback<Collect>() {
            override fun areItemsTheSame(oldItem: Collect, newItem: Collect): Boolean {
                return oldItem.collectNo == newItem.collectNo
            }

            override fun areContentsTheSame(oldItem: Collect, newItem: Collect): Boolean {
                return oldItem == newItem
            }
        }
    }
}