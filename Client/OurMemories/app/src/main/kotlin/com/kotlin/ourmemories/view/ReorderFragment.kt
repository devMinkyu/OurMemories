package com.kotlin.ourmemories.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class ReorderFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_reorder, container, false)

}