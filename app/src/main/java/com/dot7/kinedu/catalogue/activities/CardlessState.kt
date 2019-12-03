package com.banregio.heybanco.cardless

import com.banregio.heybanco.model.ResponseModel
import org.json.JSONObject
import retrofit2.Response

/**
 * Estados para Cardless
 */
sealed class CardlessState {
    object ShowInternetAlertRetry : CardlessState()
    class ShowErrorData(var errorInfo: String) : CardlessState()
    class ShowErrorMessage(var errorMessage: String) : CardlessState()
    class ShowData(var response: Response<ResponseModel>, var accountJson: JSONObject,
                   var idAccount: String = "", var refreshType: Int = 0,
                   var isError: Boolean = false) : CardlessState()
}