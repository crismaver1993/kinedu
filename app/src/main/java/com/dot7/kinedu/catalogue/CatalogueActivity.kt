package com.dot7.kinedu.catalogue

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dot7.kinedu.BaseActivity
import com.dot7.kinedu.R
import com.dot7.kinedu.catalogue.activities.ActivitiesFragment
import com.dot7.kinedu.catalogue.articles.ArticlesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar.*


class CatalogueActivity : BaseActivity() {

    private var isSpinnerTouched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAll()
    }

    /**
     * Initialize variables, objects and functions
     */
    private fun initAll() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white) // Hardcoded back arrow icon
        changeTitle(getString(R.string.title_crawling))

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_catalogue) // I did
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_catalogue -> {
                }

                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        addSpinner()
    }

    /**
     * Setup spinner functionality
     */
    private fun addSpinner() {
        val spinner = findViewById<Spinner>(R.id.sp_ages)

        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.ages_list)
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = adapter

        spinner.setOnTouchListener { _, _ ->
            isSpinnerTouched = true
            false
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {
                view?.let {
                    if (isSpinnerTouched) {
                        isSpinnerTouched = false
                        filterAge(position)
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    /**
     * Filter activities and articles by age
     * for Activities & Articles fragment
     */
    private fun filterAge(age: Int) {
        supportFragmentManager.fragments.forEach { mFragment ->
            when (mFragment) {
                is ActivitiesFragment -> {
                    mFragment.filterAge(age)
                }
                is ArticlesFragment -> {
                    mFragment.filterAge(age)
                }
            }
        }
    }

    /**
     * You can change the current title of MainActivity thrown this function
     */
    fun changeTitle(title: String) {
        tv_toolbar_title.text = title
    }

    /**
     * Try to reload data when Internet returns
     * for Activities & Articles fragment
     */
    fun reloadFragments() {
        supportFragmentManager.fragments.forEach { mFragment ->
            when (mFragment) {
                is ActivitiesFragment -> {
                    mFragment.refreshData()
                }
                is ArticlesFragment -> {
                    mFragment.refreshData()
                }
            }
        }
    }
}