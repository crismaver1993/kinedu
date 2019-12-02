package com.dot7.kinedu.family

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dot7.kinedu.BaseFragment
import com.dot7.kinedu.R

class MyFamilyFragment : BaseFragment() {

    private lateinit var notificationsViewModel: MyFamilyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(MyFamilyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_family, container, false)
        val textView: TextView = root.findViewById(R.id.tv_my_family_info)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}