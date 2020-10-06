package com.lesniak.financialinputfields

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class MainViewModel : ViewModel() {

    // Unformatted sort code received here 000000
    val sortCode = MutableLiveData<String>()
    val amount = MutableLiveData<BigDecimal>()
    val amountString = Transformations.map(amount) {
        it.toString()
    }

    init {
        sortCode.value = "00-00000"
    }
}