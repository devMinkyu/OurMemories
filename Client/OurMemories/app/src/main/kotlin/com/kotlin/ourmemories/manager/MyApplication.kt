package com.kotlin.ourmemories.manager

import android.app.Application
import android.content.Context

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
class MyApplication : Application(){
    companion object {
        lateinit var context:Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}