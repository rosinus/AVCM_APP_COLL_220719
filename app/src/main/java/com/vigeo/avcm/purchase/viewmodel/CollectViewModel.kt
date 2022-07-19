package com.vigeo.avcm.purchase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigeo.avcm.purchase.data.model.CollectionList
import com.vigeo.avcm.purchase.repository.CollectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectViewModel(
    private val collectRepository : CollectRepository
) : ViewModel() {
    private val _result = MutableLiveData<CollectionList>()
    val result : LiveData<CollectionList> get() = _result

    private val _resultDetail = MutableLiveData<CollectionList>()
    val resultDetail : LiveData<CollectionList> get() = _resultDetail

    fun collectionLists( userNo:String ) = viewModelScope.launch(Dispatchers.IO) {
        val response = collectRepository.collectList("O304UIUw3P78ZZPC5qBkmQ==", userNo)
        if(response.isSuccessful){
            response.body()?.let { body ->
                _result.postValue(body)
            }
        }
    }

    fun collectionDetail( userNo:String) = viewModelScope.launch(Dispatchers.IO) {
        val response = collectRepository.collectDetail("O304UIUw3P78ZZPC5qBkmQ==", userNo)
        if(response.isSuccessful){
            response.body()?.let { body ->
                _resultDetail.postValue(body)
            }
        }
    }
}