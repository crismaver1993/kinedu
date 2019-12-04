package com.dot7.kinedu.catalogue.activities

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dot7.kinedu.viewModel.ScreenState

/**
 * Activities viewmodel
 */
class ActivitiesViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ActivitiesRepository(app)

    val observerResponse: LiveData<ScreenState<ActivitiesState>>
        get() {
            return repository.activitiesState
        }

    fun getActivities(mContext: Context) {
        repository.getActivities(mContext)
    }

}