package com.lesniak.financialinputfields

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // Unformatted sort code received here 000000
    val sortCode = MutableLiveData<String>()

    init {
        sortCode.value = "00-00000"
    }
}