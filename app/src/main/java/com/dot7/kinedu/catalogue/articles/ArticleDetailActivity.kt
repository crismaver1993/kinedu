package com.dot7.kinedu.catalogue.articles

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dot7.kinedu.BaseActivity
import com.dot7.kinedu.R
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.models.KineduArticleDetailResponse
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView
import kotlinx.android.synthetic.main.activity_article_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleDetailActivity : BaseActivity() {
    private var idAccount = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        initAll()
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        val bundle = intent
        val imgArticle: RectangleImageView = findViewById(R.id.iv_article_detail_cover)

        if (bundle != null) {
            val articleInfo =
                bundle.getParcelableExtra<ArticleInfoData>(KineduConstants.ARTICLE_MODEL)
            articleInfo?.let {
                idAccount = it.id.toString()
                Glide.with(this@ArticleDetailActivity)
                    .load(it.picture)
                    .into(imgArticle)
                tv_article_detail_info.text = it.name
            }
            if (isOnline(this)) {
                val call = apiService?.getArticleDetail(
                    KineduConstants.TOKEN,
                    idAccount
                )
                showProgressBar()
                call?.enqueue(object : Callback<KineduArticleDetailResponse> {
                    override fun onFailure(call: Call<KineduArticleDetailResponse>, t: Throwable) {
                        dismissProgressBar()
                    }

                    override fun onResponse(
                        call: Call<KineduArticleDetailResponse>,
                        response: Response<KineduArticleDetailResponse>
                    ) {
                        showInfo(response)
                    }
                })
            }
        }
    }


    private fun showInfo(response: Response<KineduArticleDetailResponse>) {
        this@ArticleDetailActivity?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tv_article_detail_body.text = body.data.article.body.toSpannedText()
                    dismissProgressBar()
                }
            }
        }
    }
}