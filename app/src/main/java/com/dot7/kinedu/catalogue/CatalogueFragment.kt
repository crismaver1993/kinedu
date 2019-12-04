package com.dot7.kinedu.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R
import com.google.android.material.tabs.TabLayout

class CatalogueFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exercise_home, container, false)
        root?.let { initViews(it) }
        return root
    }

    /**
     * Initialize variables, objects and functions
     * @param rootView instance de current view inflated
     */
    private fun initViews(rootView: View) {
        this@CatalogueFragment.context?.let { mContext ->
            val viewPager: ViewPager = rootView.findViewById(R.id.view_pager)
            val tabs = rootView.findViewById<TabLayout>(R.id.tabs)

            val mSupportManager = activity?.supportFragmentManager
            mSupportManager?.let {
                val sectionsPagerAdapter = SectionsPagerAdapter(mContext, it)
                viewPager.adapter = sectionsPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
        }
    }
}