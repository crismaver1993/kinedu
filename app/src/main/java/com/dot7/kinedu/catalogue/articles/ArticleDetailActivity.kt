package com.dot7.kinedu.catalogue.articles

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dot7.kinedu.BaseActivity
import com.dot7.kinedu.R
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.models.KineduArticleDetailResponse
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView
import com.dot7.kinedu.viewModel.ScreenState
import kotlinx.android.synthetic.main.activity_article_detail.*


class ArticleDetailActivity : BaseActivity() {
    private lateinit var articlesViewModel: ArticlesViewModel
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
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        articlesViewModel.observerResponse.observe(this, Observer { onChanged(it) })

        initToolbar()
        val bundle = intent
        val imgArticle: RectangleImageView = findViewById(R.id.iv_article_detail_cover)

        bundle?.let { mBundle->
            val articleInfo =
                mBundle.getParcelableExtra<ArticleInfoData>(KineduConstants.ARTICLE_MODEL)
            articleInfo?.let {
                idAccount = it.id.toString()
                Glide.with(this@ArticleDetailActivity)
                    .load(it.picture)
                    .into(imgArticle)
                tv_article_detail_info.text = it.name
            }

            bv_share_detail.setOnClickListener { shareIntent() }
            getArticleDetail()
        }
    }

    /**
     * Custom toolbar
     */
    private fun initToolbar() {
        val mToolbar =  findViewById<Toolbar>(R.id.toolbar_article_detail)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        mToolbar.setNavigationOnClickListener{ onBackPressed()}
    }

    /**
     * Start consuming webservices
     */
    private fun getArticleDetail() {
        this@ArticleDetailActivity.let {
            if (isOnline(it)) {
                articlesViewModel.getArtcileDetail(it,idAccount)
            } else {
                noInternet()
            }
        }
    }

    /**
     * Show Snack bar to notify the user about no internet connection
     */
    private fun noInternet() {
        this@ArticleDetailActivity.let { mActivity ->
            (mActivity as BaseActivity).showSnackError(
                R.string.msg_no_internet_error,
                R.string.label_retry,
                View.OnClickListener { })
        }
    }

    /**
     * It is observing activitiesViewModel state
     * @param screenState screen state type to be validate
     */
    private fun onChanged(screenState: ScreenState<ArticlesState>?) {
        screenState?.let {
            updateUI(screenState)
        }
    }

    /**
     * Check what kind of renderState it is
     * @param renderState action type to execute
     */
    private fun updateUI(renderState: ScreenState<ArticlesState>) {
        this@ArticleDetailActivity.let {
            when (renderState) {
                is ScreenState.Loading -> {
                    showProgressBar()
                }

                is ScreenState.Render -> {
                    when (renderState.renderState) {
                        is ArticlesState.ShowArticleDetail -> {
                            val body =
                                (renderState.renderState as ArticlesState.ShowArticleDetail).detail
                            showInfo(body)
                        }
                        is ArticlesState.ShowInternetAlertRetry -> {
                            dismissProgressBar()
                            noInternet()
                        }
                    }
                }

                is ScreenState.ErrorServer -> {
                    Toast.makeText(it, getString(R.string.msg_error), Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }

                else -> {
                    dismissProgressBar()
                }
            }
        }
    }

    /**
     * Show text info
     * @param body answer from service
     */
    private fun showInfo(body: KineduArticleDetailResponse) {
        this@ArticleDetailActivity.let {
                    tv_article_detail_body.text = body.data.article.body.toSpannedText()
                    tv_article_detail_body.movementMethod = LinkMovementMethod.getInstance()
                    dismissProgressBar()
        }
    }

    /**
     * Share content text in Social Networks
     */
    fun shareIntent() {
        val myText =   "#KineduApp \n\n${tv_article_detail_body.text}"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, tv_article_detail_body.text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}