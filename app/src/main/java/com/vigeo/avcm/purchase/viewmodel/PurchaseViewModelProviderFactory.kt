package com.vigeo.avcm.purchase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vigeo.avcm.purchase.repository.PurchaseRepository

@Suppress("UNCHECKED_CAST")
class PurchaseViewModelProviderFactory(
    private val PurchaseRepository : PurchaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseViewModel::class.java)) {
            return PurchaseViewModel(PurchaseRepository) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}