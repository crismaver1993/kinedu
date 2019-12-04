package com.dot7.kinedu.catalogue.articles

import com.dot7.kinedu.models.ArticleInfoData

/**
 *  Activities State
 */
sealed class ArticlesState {
    object ShowInternetAlertRetry : ArticlesState()
    class ShowArticles(var articles: MutableList<ArticleInfoData>) : ArticlesState()
}