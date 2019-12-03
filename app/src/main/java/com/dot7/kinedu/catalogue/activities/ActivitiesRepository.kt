package com.dot7.kinedu.catalogue.activities

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.dot7.kinedu.models.KineduActivityResponse
import com.dot7.kinedu.network.KineduClient
import com.dot7.kinedu.network.KineduService
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.viewModel.ScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activities repository
 */
class ActivitiesRepository(val context: Context) {
    val activitiesState: MutableLiveData<ScreenState<ActivitiesState>> = MutableLiveData()
    private var apiService: KineduService? = null

    init {
        apiService = KineduClient.getKineduNetworkClient()?.create(KineduService::class.java)
    }

    /**
     * get all activities
     */
    fun getActivities(mContext: Context) {
        val call = apiService?.getActivities(
            KineduConstants.TOKEN,
            KineduConstants.SKILL_ID,
            KineduConstants.BABY_ID
        )
        activitiesState.value = ScreenState.Loading
        call?.enqueue(object : Callback<KineduActivityResponse> {
            override fun onFailure(call: Call<KineduActivityResponse>, t: Throwable) {
                activitiesState.value = ScreenState.ErrorServer
            }

            override fun onResponse(
                call: Call<KineduActivityResponse>,
                response: Response<KineduActivityResponse>
            ) {
                showInfo(mContext, response)
            }
        })
    }


    /**
     * Show the activities
     *
     *@param response answer of the service to be validate and show
     */
    private fun showInfo(mContext: Context, response: Response<KineduActivityResponse>) {
        mContext?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.let {
                        activitiesState.value =
                            ScreenState.Render(ActivitiesState.ShowActivities(it.activities))
                    }
                }
            }
        }
    }


}