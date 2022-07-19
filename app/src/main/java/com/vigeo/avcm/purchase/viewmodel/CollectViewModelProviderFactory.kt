package com.vigeo.avcm.purchase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.purchase.repository.CollectRepository
import com.vigeo.avcm.purchase.repository.PurchaseRepository

@Suppress("UNCHECKED_CAST")
class CollectViewModelProviderFactory(
    private val collectRepository : CollectRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectViewModel::class.java)) {
            return CollectViewModel(collectRepository) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}