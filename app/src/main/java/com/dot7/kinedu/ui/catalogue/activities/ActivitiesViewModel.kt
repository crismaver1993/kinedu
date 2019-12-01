package com.dot7.kinedu.ui.catalogue.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ActivitiesViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()


    fun setIndex(index: Int) {
        _index.value = index
    }
}