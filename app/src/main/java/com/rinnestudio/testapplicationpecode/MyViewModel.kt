package com.rinnestudio.testapplicationpecode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val destination = MutableLiveData<Int>()
}