package com.kotlin.ourmemories.view.memory.presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.view.memory.MemoryFragment

/**
 * Created by kimmingyu on 2017. 11. 10..
 */
class MemoryPresenter:MemoryContract.Presenter {
    lateinit override var fragment: MemoryFragment

    override fun intentActivity(activity: AppCompatActivity) {
        fragment.startActivity(Intent(fragment.context, activity::class.java))
    }
}