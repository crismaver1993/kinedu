package com.dot7.kinedu.ui.catalogue.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.dot7.kinedu.R

/**
 * Show all activities for the exercise selected
 */
class ActivitiesFragment : Fragment() {
    private lateinit var activitiesViewModel: ActivitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        activitiesViewModel =
            ViewModelProviders.of(this).get(ActivitiesViewModel::class.java).apply {
                setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_activities, container, false)
        rootView?.let { initViews(rootView) }
        return rootView
    }

    /**
     * Initialize all layout and views inside on it
     * @param rootView instance of the current view inflated
     */
    private fun initViews(rootView: View) {
        val rvActivities: RecyclerView = rootView.findViewById(R.id.rv_activities)

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ActivitiesFragment {
            return ActivitiesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}