package com.banregio.heybanco.cardless

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.banregio.heybanco.model.ResponseAccountModel
import com.banregio.heybanco.model.ResponseModel
import com.banregio.heybanco.network.HeyApiService
import com.banregio.heybanco.network.HeyNetworkClient
import com.banregio.heybanco.services.NetworkUtils
import com.banregio.heybanco.services.Utils
import com.banregio.heybanco.ui.verifyAvailableNetwork
import com.banregio.heybanco.utils.Constants
import com.banregio.heybanco.utils.WSErrorUtil
import com.banregio.heybanco.viewModel.ScreenState
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repositorio de AccountBaseDataFragment
 */
class CardlessRepository(val mContext: Context) {
    val cardlessState: MutableLiveData<ScreenState<CardlessState>> = MutableLiveData()
    private var apiService: HeyApiService? = null

    init {
        apiService = HeyNetworkClient.heyNetworkClient(mContext)?.create(HeyApiService::class.java)
    }



}