package com.dot7.kinedu.catalogue.articles

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dot7.kinedu.models.KineduActivityResponse
import com.dot7.kinedu.models.KineduArticleResponse
import com.dot7.kinedu.network.KineduClient
import com.dot7.kinedu.network.KineduService
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.viewModel.ScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Articles repository
 */
class ArticlesRepository(val context: Context) {
    val articlesState: MutableLiveData<ScreenState<ArticlesState>> = MutableLiveData()
    private var apiService: KineduService? = null

    init {
        apiService = KineduClient.getKineduNetworkClient()?.create(KineduService::class.java)
    }

    /**
     * get all articles
     * @param mContext current context of user
     */
    fun getArticles(mContext: Context) {
        val call = apiService?.getArticles(
            KineduConstants.TOKEN,
            KineduConstants.SKILL_ID,
            KineduConstants.BABY_ID
        )
        articlesState.value = ScreenState.Loading
        call?.enqueue(object : Callback<KineduArticleResponse> {
            override fun onFailure(call: Call<KineduArticleResponse>, t: Throwable) {
                articlesState.value = ScreenState.ErrorServer
            }

            override fun onResponse(
                call: Call<KineduArticleResponse>,
                response: Response<KineduArticleResponse>
            ) {
                showInfo(mContext,response)
            }
        })
    }

    /**
     * Show the article list
     *
     *@param response answer of the service to be validate and show
     */
    private fun showInfo(mContext: Context, response: Response<KineduArticleResponse>) {
        mContext?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.let {
                        articlesState.value =
                            ScreenState.Render(ArticlesState.ShowArticles(it.articles))
                    }
                } else {
                    articlesState.value = ScreenState.ErrorServer
                }
            }
        }
    }
}