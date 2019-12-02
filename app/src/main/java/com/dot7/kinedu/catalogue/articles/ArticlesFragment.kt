package com.dot7.kinedu.catalogue.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dot7.kinedu.R

/**
 * A placeholder fragment containing a simple view.
 */
class ArticlesFragment : Fragment() {

    private lateinit var pageViewModel: ArticlesViewModel
    private lateinit var  rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_articles, container, false)
        rootView?.let{initViews(it)}
        return rootView
    }
    /**
    * Initialize all layout and views inside on it
    * @param rootView instance of the current view inflated
    */
    private fun initViews(rootView: View) {

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