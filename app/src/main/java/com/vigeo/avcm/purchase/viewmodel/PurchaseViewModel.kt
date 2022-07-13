package com.vigeo.avcm.purchase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigeo.avcm.purchase.repository.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PurchaseViewModel(
    private val PurchaseRepository : PurchaseRepository
) : ViewModel() {
    //private val result = MutableLiveData<T>()

    fun serchPurchase() = viewModelScope.launch(Dispatchers.IO) {

    }
}