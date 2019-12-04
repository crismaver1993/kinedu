package com.dot7.kinedu.catalogue.articles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dot7.kinedu.BaseActivity
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R
import com.dot7.kinedu.catalogue.CatalogueActivity
import com.dot7.kinedu.interfaces.OnExerciseListener
import com.dot7.kinedu.models.ActivityDataInfo
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView
import com.dot7.kinedu.viewModel.ScreenState


/**
 * A placeholder fragment containing a simple view.
 */
class ArticlesFragment : BaseFragment(), OnExerciseListener {
    private lateinit var articlesViewModel: ArticlesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rootView: View
    private lateinit var rvArticles: RecyclerView
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var flArticles: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_articles, container, false)
        initViews(rootView)
        return rootView
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        articlesViewModel.observerResponse.observe(this, Observer { onChanged(it) })
    }

    /**
     * Initialize all layout and views inside on it
     * @param rootView instance of the current view inflated
     */
    private fun initViews(rootView: View) {
        this@ArticlesFragment.context?.let { mContext ->
            rvArticles = rootView.findViewById(R.id.rv_articles)
            flArticles = rootView.findViewById(R.id.fl_articles_no_found)
            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            articlesAdapter = ArticlesAdapter(mContext, this)
            rvArticles.setHasFixedSize(true)
            rvArticles.layoutManager = LinearLayoutManager(mContext)
            articlesAdapter.let { rvArticles.adapter = it }
            swipeRefreshLayout =  rootView.findViewById(R.id.swipe_refresh_articles)
            swipeRefreshLayout.setOnRefreshListener {
                getArticles()
            }
            getArticles()
        }
    }

    fun refreshData() {
        if (articlesAdapter.itemCount <= 0) {
            getArticles()
        }
    }

    /**
     * Start consuming webservices
     */
    private fun getArticles() {
        this@ArticlesFragment.context?.let {
            if (isOnline(it)) {
                articlesViewModel.getArticles(it)
            } else {
                noInternet()
            }
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
        this@ArticlesFragment.context?.let {
            when (renderState) {
                is ScreenState.Loading -> {
                    showProgressBar()
                }

                is ScreenState.Render -> {
                    when (renderState.renderState) {
                        is ArticlesState.ShowArticles -> {
                            val list = (renderState.renderState as ArticlesState.ShowArticles).articles
                            articlesAdapter.setListInfo(list)
                            dismissProgressBar()
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
     * onExerciseListener function
     *
     * Show exercise name and the correct age to start doing it
     */
    override fun showArticleDetail(
        articleInfoData: ArticleInfoData,
        rectangleImageView: RectangleImageView
    ) {
        this@ArticlesFragment.context?.let {
            val intent = Intent(it, ArticleDetailActivity::class.java)
            intent.putExtra(KineduConstants.ARTICLE_MODEL, articleInfoData)
            val options = activity?.let { mActivity ->
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    mActivity,
                    rectangleImageView, KineduConstants.ARTICLE_MODEL_ANIM
                )
            }
            startActivity(intent, options?.toBundle())
        }
    }

    override fun updateView(count: Int) {
        swipeRefreshLayout.isEnabled = false
        if (articlesAdapter.itemCount <= 0) {
            flArticles.visibility = View.VISIBLE
            rvArticles.visibility = View.GONE
        } else {
            flArticles.visibility = View.GONE
            rvArticles.visibility = View.VISIBLE
        }
    }

    /**
     * Show Snack bar to notify the user about no internet connection
     */
    private fun noInternet() {
        swipeRefreshLayout.isEnabled = false
        (activity as BaseActivity).showSnackError(
            R.string.msg_no_internet_error,
            R.string.label_retry,
            View.OnClickListener { (activity as CatalogueActivity).reloadFragments()})
    }

    override fun showActivityDetail(activityInfo: ActivityDataInfo) {
        // N/A
    }

    /**
     * Filter articles by age
     * @param age to filter
     */
    fun filterAge(age: Int) {
        articlesAdapter?.filterList(age)
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