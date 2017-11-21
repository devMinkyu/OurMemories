package com.kotlin.ourmemories.view.memory.presenter

import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.view.memory.MemoryFragment

/**
 * Created by kimmingyu on 2017. 11. 10..
 */
interface MemoryContract {
    interface View
    interface Presenter{
        var fragment:MemoryFragment
        var mView:MemoryContract.View

        fun intentActivity(activity: AppCompatActivity)
    }
}