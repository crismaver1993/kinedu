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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_articles, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        return root
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