package com.dot7.kinedu.catalogue.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R
import com.dot7.kinedu.interfaces.onExerciseListener
import com.dot7.kinedu.models.ActivityDataInfo
import com.dot7.kinedu.models.KineduResponse
import com.dot7.kinedu.util.KineduConstants
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Show all activities for the exercise selected
 */
class ActivitiesFragment : BaseFragment(), onExerciseListener {
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
            activitiesAdapter = ActivitiesAdapter(mContext, this)
            rvActivities.setHasFixedSize(true)
            rvActivities.layoutManager = LinearLayoutManager(mContext)
            activitiesAdapter?.let { rvActivities.adapter = it }

            if (isOnline(mContext)) {
                val call = apiService?.getActivities(
                    KineduConstants.TOKEN,
                    KineduConstants.SKILL_ID,
                    KineduConstants.BABY_ID
                )
                call?.enqueue(object : Callback<KineduResponse> {
                    override fun onFailure(call: Call<KineduResponse>, t: Throwable) {
                        Log.v("xxxError", "${t.cause}")
                    }

                    override fun onResponse(
                        call: Call<KineduResponse>,
                        response: Response<KineduResponse>
                    ) {
                        showInfo(response)
                    }
                })
            }
        }
    }

    /**
     * Show the activities
     */
    private fun showInfo(response: Response<KineduResponse>) {
        this@ActivitiesFragment.context?.let {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    activitiesAdapter.setListInfo(body.data.activities)
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
            Snackbar.make(
                rootView.findViewById(R.id.ln_activities_container),
                "Exercise ${activityInfo.name} for ages of ${activityInfo.age} and up",
                Snackbar.LENGTH_LONG
            )
                .setAction("Ok") { }
                .setActionTextColor(ContextCompat.getColor(it, R.color.colorAccent))
                .show()
        }
    }
}