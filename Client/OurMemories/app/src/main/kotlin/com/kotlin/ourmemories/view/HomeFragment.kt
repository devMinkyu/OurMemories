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
        // 뷰페이지가 바꼈을 때 tab에게 알려주는 곳 그래야 색깔이 변경되기 때문에
        homeContainer.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        homeContainer.currentItem = 1

        tabs.run {
            addTab(tabs.newTab().setText("알람"))
            addTab(tabs.newTab().setText("홈"))
            addTab(tabs.newTab().setText("추천장소"))

//            tab을 눌렀을 때 뷰페이지한테 알려줘서 뷰페이지를 바꾸라고 알려주는곳
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabSelected(tab: TabLayout.Tab) {
                    var postion:Int =tab.position
                    homeContainer.currentItem = postion
                }

            })
        }

    }


}