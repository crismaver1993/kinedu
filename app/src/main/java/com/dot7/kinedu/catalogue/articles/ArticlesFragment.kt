package com.dot7.kinedu.catalogue.articles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R
import com.dot7.kinedu.interfaces.OnExerciseListener
import com.dot7.kinedu.models.ActivityDataInfo
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.models.KineduArticleResponse
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A placeholder fragment containing a simple view.
 */
class ArticlesFragment : BaseFragment(), OnExerciseListener {

    private lateinit var pageViewModel: ArticlesViewModel
    private lateinit var rootView: View
    private lateinit var rvArticles: RecyclerView
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_articles, container, false)
        rootView?.let { initViews(it) }
        return rootView
    }

    /**
     * Initialize all layout and views inside on it
     * @param rootView instance of the current view inflated
     */
    private fun initViews(rootView: View) {
        this@ArticlesFragment.context?.let { mContext ->
            rvArticles = rootView.findViewById(R.id.rv_articles)
            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            articlesAdapter = ArticlesAdapter(mContext, this)
            rvArticles.setHasFixedSize(true)
            rvArticles.layoutManager = LinearLayoutManager(mContext)
            articlesAdapter?.let { rvArticles.adapter = it }

            if (isOnline(mContext)) {
                val call = apiService?.getArticles(
                    KineduConstants.TOKEN,
                    KineduConstants.SKILL_ID,
                    KineduConstants.BABY_ID
                )
                showProgressBar()
                call?.enqueue(object : Callback<KineduArticleResponse> {
                    override fun onFailure(call: Call<KineduArticleResponse>, t: Throwable) {
                        Log.v("xxxError", "${t.cause}")
                        dismissProgressBar()
                    }

                    override fun onResponse(
                        call: Call<KineduArticleResponse>,
                        response: Response<KineduArticleResponse>
                    ) {
                        showInfo(response)
                    }
                })
            }
        }
    }


    private fun showInfo(response: Response<KineduArticleResponse>) {
        this@ArticlesFragment.context?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    articlesAdapter.setListInfo(body.data.articles)
                    dismissProgressBar()
                }
            }
        }
    }

    override fun showActivityDetail(activityInfo: ActivityDataInfo) {
//
    }

    /**
     * onExerciseListener function
     *
     * Show exercise name and the correct age to start doing it
     */
    override fun showArticleDetail(
        activityInfo: ArticleInfoData,
        rectangleImageView: RectangleImageView
    ) {
        this@ArticlesFragment.context?.let {
            val intent = Intent(it, ArticleDetailActivity::class.java)
            intent.putExtra("ArticleModel", activityInfo)
            val options = activity?.let { mActivity ->
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    mActivity,
                    rectangleImageView, "imgArticle"
                )
            }
            startActivity(intent, options?.toBundle())
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }

}