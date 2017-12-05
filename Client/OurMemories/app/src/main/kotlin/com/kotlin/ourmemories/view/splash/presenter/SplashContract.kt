package com.kotlin.ourmemories.view.splash.presenter

import android.os.Handler
import com.kotlin.ourmemories.data.source.autologin.AutoLoginRepository
import com.kotlin.ourmemories.view.splash.SplashActivity


/**
 * Created by kimmingyu on 2017. 11. 2..
 */
interface SplashContract {
    interface Presenter{
        val mHandler: Handler
        var activity: SplashActivity
        var profileData: AutoLoginRepository

        fun autoLogin()
        fun isLoginCheck()
    }
}