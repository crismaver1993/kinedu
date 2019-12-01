package com.dot7.kinedu.catalogue.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivitiesViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()


    fun setIndex(index: Int) {
        _index.value = index
    }
}