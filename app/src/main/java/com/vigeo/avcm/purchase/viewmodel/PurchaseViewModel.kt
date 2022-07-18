package com.vigeo.avcm.purchase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigeo.avcm.purchase.data.model.PurchaseList
import com.vigeo.avcm.purchase.repository.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PurchaseViewModel(
    private val purchaseRepository : PurchaseRepository
) : ViewModel() {
    private val _result = MutableLiveData<PurchaseList>()
    val result : LiveData<PurchaseList> get() = _result

    private val _resultDetail = MutableLiveData<PurchaseList>()
    val resultDetail : LiveData<PurchaseList> get() = _resultDetail


    fun purchaseLists(    prodGb:String,
                          prodNm:String,
                          firstIndex:Int,
                          lastIndex:Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = purchaseRepository.purchaseList("O304UIUw3P78ZZPC5qBkmQ==",prodNm,prodGb,firstIndex,lastIndex)
        if(response.isSuccessful){
            response.body()?.let { body ->
                _result.postValue(body)
            }
        }
    }

    fun purchaseDetail( prodNo:String) = viewModelScope.launch(Dispatchers.IO) {
        val response = purchaseRepository.purchaseDetail("O304UIUw3P78ZZPC5qBkmQ==",prodNo,0,1)
        if(response.isSuccessful){
            response.body()?.let { body ->
                _resultDetail.postValue(body)
            }
        }
    }
}