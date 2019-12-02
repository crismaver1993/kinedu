package com.dot7.kinedu.catalogue.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dot7.kinedu.R
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView

class ArticleDetailActivity : AppCompatActivity() {

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
        val articleInfo = bundle.getParcelableExtra<ArticleInfoData>(KineduConstants.ARTICLE_MODEL)

        articleInfo?.let {
            Glide.with(this@ArticleDetailActivity)
                .load(it.picture)
                .into(imgArticle)
        }
    }
}