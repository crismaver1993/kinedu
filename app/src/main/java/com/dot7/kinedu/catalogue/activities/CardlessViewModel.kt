package com.banregio.heybanco.cardless

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.banregio.heybanco.viewModel.ScreenState

/**
 * ViewModel de AccountBaseDataFragment
 */
class CardlessViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = CardlessRepository(app)

   val observerResponse: LiveData<ScreenState<CardlessState>>
        get(){
            return   repository.cardlessState
        }


}