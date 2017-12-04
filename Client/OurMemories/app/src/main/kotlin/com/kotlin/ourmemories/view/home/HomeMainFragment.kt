package com.kotlin.ourmemories.view.home

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.memory.MemoryFragment
import com.kotlin.ourmemories.view.memorylist.MemoryListFragment
import com.kotlin.ourmemories.view.memorypin.MemoryPinFragment
import kotlinx.android.synthetic.main.fragment_home_main.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class HomeMainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_home_main, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        // 폰트 변경
        val canaroExtraBold = Typeface.createFromAsset(activity.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        titleText.typeface = canaroExtraBold
        pinText.typeface = canaroExtraBold

        val main = activity as MainActivity
        mainPin.setOnClickListener {
            main.changeFragment(0,MemoryPinFragment())
        }
        mainEnroll.setOnClickListener {
            main.changeFragment(1,MemoryFragment())
        }
        mainList.setOnClickListener {
            main.changeFragment(2,MemoryListFragment())
        }
    }

}