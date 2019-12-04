package com.dot7.kinedu.catalogue.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
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
import com.dot7.kinedu.util.customview.RectangleImageView
import com.dot7.kinedu.viewModel.ScreenState

/**
 * Show all activities for the exercise selected
 */
class ActivitiesFragment : BaseFragment(), OnExerciseListener {
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var rvActivities: RecyclerView
    private lateinit var activitiesAdapter: ActivitiesAdapter
    private lateinit var rootView: View
    private lateinit var tvActivitiesNotFound: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesViewModel::class.java)
        activitiesViewModel.observerResponse.observe(this, Observer { onChanged(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_activities, container, false)
        initViews(rootView)
        return rootView
    }

    /**
     * Initialize all layout and views inside on it
     * @param rootView instance of the current view inflated
     */
    private fun initViews(rootView: View) {
        this@ActivitiesFragment.context?.let { mContext ->
            rvActivities = rootView.findViewById(R.id.rv_activities)
            tvActivitiesNotFound = rootView.findViewById(R.id.tv_activities_no_found)
            val learnMore = rootView.findViewById<CardView>(R.id.cv_activities_info)

            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            activitiesAdapter = ActivitiesAdapter(mContext, this)
            rvActivities.setHasFixedSize(true)
            rvActivities.layoutManager = LinearLayoutManager(mContext)
            activitiesAdapter.let { rvActivities.adapter = it }

            learnMore.setOnClickListener {
                Toast.makeText(
                    mContext,
                    getString(R.string.msg_learn_more_click),
                    Toast.LENGTH_SHORT
                ).show()
            }

            val swipeRefreshLayout =  rootView.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_activities)
            swipeRefreshLayout.setOnRefreshListener {
                getActivities()
            }
            getActivities()
        }
    }

    fun refreshData() {
        if (activitiesAdapter.itemCount <= 0) {
            getActivities()
        }
    }

    /**
     * Start consuming webservices
     */
    private fun getActivities() {
        this@ActivitiesFragment.context?.let {
            if (isOnline(it)) {
                activitiesViewModel.getActivities(it)
            } else {
                noInternet()
            }
        }
    }

    /**
     * It is observing activitiesViewModel state
     * @param screenState screen state type to be validate
     */
    private fun onChanged(screenState: ScreenState<ActivitiesState>?) {
        screenState?.let {
            updateUI(screenState)
        }
    }

    /**
     * Check what kind of renderState it is
     * @param renderState action type to execute
     */
    private fun updateUI(renderState: ScreenState<ActivitiesState>) {
        this@ActivitiesFragment.context?.let {
            when (renderState) {
                is ScreenState.Loading -> {
                    showProgressBar()
                }

                is ScreenState.Render -> {
                    when (renderState.renderState) {
                        is ActivitiesState.ShowActivities -> {
                            activitiesAdapter.setListInfo((renderState.renderState as ActivitiesState.ShowActivities).activities)
                            dismissProgressBar()
                        }
                        is ActivitiesState.ShowInternetAlertRetry -> {
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
     * Show Snack bar to notify the user about no internet connection
     */
    private fun noInternet() {
        (activity as BaseActivity).showSnackError(
            R.string.msg_no_internet_error,
            R.string.label_retry,
            View.OnClickListener { (activity as CatalogueActivity).reloadFragments() })
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): ActivitiesFragment {
            return ActivitiesFragment()
        }
    }

    /**
     * onExerciseListener function
     *
     * Show exercise name and the correct age to start doing it
     */
    override fun showActivityDetail(activityInfo: ActivityDataInfo) {
        this@ActivitiesFragment.context?.let {
            showSnackBar(
                it,
                rootView.findViewById(R.id.ln_activities_container),
                "ok",
                "Exercise ${activityInfo.name} for ages of ${activityInfo.age} and up"
            )
        }
    }

    override fun showArticleDetail(
        articleInfoData: ArticleInfoData,
        rectangleImageView: RectangleImageView
    ) { //    N/A }
    }

    override fun updateView(count: Int) {
        if (activitiesAdapter.itemCount <= 0) {
            tvActivitiesNotFound.visibility = View.VISIBLE
            rvActivities.visibility = View.GONE
        } else {
            tvActivitiesNotFound.visibility = View.GONE
            rvActivities.visibility = View.VISIBLE
        }
    }

    /**
     * Filter activities by age
     * @param age to filter
     */
    fun filterAge(age: Int) {
        activitiesAdapter?.filterList(age)
    }
}