package com.kotlin.ourmemories.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.adapter.pagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class HomeFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        homeContainer.adapter = pagerAdapter(childFragmentManager)
        homeContainer.currentItem = 0
    }
}