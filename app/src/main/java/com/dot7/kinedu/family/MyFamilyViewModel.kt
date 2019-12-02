package com.dot7.kinedu.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyFamilyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my family Fragment"
    }
    val text: LiveData<String> = _text
}