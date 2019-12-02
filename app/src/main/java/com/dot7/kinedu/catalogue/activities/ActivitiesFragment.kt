package com.dot7.kinedu.catalogue.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R
import com.dot7.kinedu.interfaces.OnExerciseListener
import com.dot7.kinedu.models.ActivityDataInfo
import com.dot7.kinedu.models.ArticleData
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.models.KineduActivityResponse
import com.dot7.kinedu.util.KineduConstants
import com.dot7.kinedu.util.customview.RectangleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Show all activities for the exercise selected
 */
class ActivitiesFragment : BaseFragment(), OnExerciseListener {
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var rvActivities: RecyclerView
    private lateinit var activitiesAdapter: ActivitiesAdapter
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_activities, container, false)
        rootView?.let { initViews(rootView) }
        return rootView
    }

    /**
     * Initialize all layout and views inside on it
     * @param rootView instance of the current view inflated
     */
    private fun initViews(rootView: View) {
        this@ActivitiesFragment.context?.let { mContext ->
            rvActivities = rootView.findViewById(R.id.rv_activities)
            val learnMore = rootView.findViewById<TextView>(R.id.tv_activities_learn_more)


            activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
            activitiesAdapter = ActivitiesAdapter(mContext, this)
            rvActivities.setHasFixedSize(true)
            rvActivities.layoutManager = LinearLayoutManager(mContext)
            activitiesAdapter?.let { rvActivities.adapter = it }

            learnMore.setOnClickListener {
                Toast.makeText(
                    mContext,
                    getString(R.string.msg_learn_more_click),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (isOnline(mContext)) {
                val call = apiService?.getActivities(
                    KineduConstants.TOKEN,
                    KineduConstants.SKILL_ID,
                    KineduConstants.BABY_ID
                )
                showProgressBar()
                call?.enqueue(object : Callback<KineduActivityResponse> {
                    override fun onFailure(call: Call<KineduActivityResponse>, t: Throwable) {
                        Log.v("xxxError", "${t.cause}")
                        dismissProgressBar()
                    }

                    override fun onResponse(
                        call: Call<KineduActivityResponse>,
                        response: Response<KineduActivityResponse>
                    ) {
                        showInfo(response)
                    }
                })
            }
        }
    }

    /**
     * Show the activities
     *
     *@param response answer of the service to be validate and show
     */
    private fun showInfo(response: Response<KineduActivityResponse>) {
        this@ActivitiesFragment.context?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    activitiesAdapter.setListInfo(body.data.activities)
                    dismissProgressBar()
                }
            }
        }
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

    override fun showArticleDetail(activityInfo: ArticleInfoData, rectangleImageView: RectangleImageView) {
     //    N/A
    }
}