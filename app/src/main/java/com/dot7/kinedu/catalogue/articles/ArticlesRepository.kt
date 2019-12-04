package com.dot7.kinedu.catalogue.articles

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.dot7.kinedu.models.KineduArticleDetailResponse
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

    fun getArticleDetail(mContext: Context, idAccount: String) {
        val call = apiService?.getArticleDetail(
            KineduConstants.TOKEN,
            idAccount
        )
        articlesState.value = ScreenState.Loading
        call?.enqueue(object : Callback<KineduArticleDetailResponse> {
            override fun onFailure(call: Call<KineduArticleDetailResponse>, t: Throwable) {
                articlesState.value = ScreenState.ErrorServer
            }

            override fun onResponse(
                call: Call<KineduArticleDetailResponse>,
                response: Response<KineduArticleDetailResponse>
            ) {
                showDetailInfo(mContext, response)
            }
        })
    }

    /**
     * Show text info
     * @param response answer from service
     */
     fun showDetailInfo(mContext: Context,response: Response<KineduArticleDetailResponse>) {
        mContext.let {
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    articlesState.value =
                        ScreenState.Render(ArticlesState.ShowArticleDetail(it))
                }
            }
        }
    }
}