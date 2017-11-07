package com.kotlin.ourmemories.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.kotlin.ourmemories.view.AlarmListFragment
import com.kotlin.ourmemories.view.HomeMainFragment
import com.kotlin.ourmemories.view.place.PlaceListFragment

/**
 * Created by kimmingyu on 2017. 11. 5..
 */

class pagerAdapter(fm: android.support.v4.app.FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? =null
        when(position){
            0-> fragment = AlarmListFragment()
            1-> fragment = HomeMainFragment()
            2-> fragment = PlaceListFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return  3
    }
}