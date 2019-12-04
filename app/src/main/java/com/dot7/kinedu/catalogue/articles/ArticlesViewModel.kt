package com.dot7.kinedu.catalogue.articles

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dot7.kinedu.viewModel.ScreenState

class ArticlesViewModel(app: Application)  : AndroidViewModel(app) {
    private val repository = ArticlesRepository(app)
    val observerResponse: LiveData<ScreenState<ArticlesState>>
        get() {
            return repository.articlesState
        }

    fun getArticles(mContext: Context) {
        repository.getArticles(mContext)
    }

    fun getArtcileDetail(mContext: Context, idAccount: String){
        repository.getArticleDetail(mContext, idAccount)
    }
}