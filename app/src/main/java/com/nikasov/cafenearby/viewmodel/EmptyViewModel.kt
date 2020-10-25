package com.nikasov.cafenearby.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmptyViewModel @ViewModelInject constructor(
): ViewModel() {

    val inProgress = MutableLiveData<Boolean>()

    fun getData() {
        inProgress.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            inProgress.postValue(false)
        }
    }
}