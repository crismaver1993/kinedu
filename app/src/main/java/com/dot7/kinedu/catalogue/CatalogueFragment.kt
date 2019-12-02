package com.dot7.kinedu.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dot7.kinedu.R
import com.google.android.material.tabs.TabLayout

class CatalogueFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_exercise_home, container, false)
        root?.let{initView(it)}
        return root
    }

    private fun initView(rootView: View) {
        this@CatalogueFragment.context?.let { mContext->
            val viewPager: ViewPager = rootView.findViewById(R.id.view_pager)
            val tabs = rootView.findViewById<TabLayout>(R.id.tabs)

            val mSupportManager = activity?.supportFragmentManager
            mSupportManager?.let {
                val sectionsPagerAdapter = SectionsPagerAdapter(mContext,it)
                viewPager.adapter = sectionsPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
        }
    }
}