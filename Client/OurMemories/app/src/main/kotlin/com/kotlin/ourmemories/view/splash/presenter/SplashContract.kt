package com.kotlin.ourmemories.view.splash.presenter

import android.os.Handler
import com.kotlin.ourmemories.view.splash.SplashActivity


/**
 * Created by kimmingyu on 2017. 11. 2..
 */
interface SplashContract {
    interface Presenter{
        var mHandler: Handler
        var activity: SplashActivity

        fun autoLogin()

        fun isLoginCheck()

        fun getProfile(userId: String)
    }
}