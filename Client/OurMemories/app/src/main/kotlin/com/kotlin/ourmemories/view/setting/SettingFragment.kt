package com.kotlin.ourmemories.view.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class SettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_setting, container, false)

}