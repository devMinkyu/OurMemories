package com.kotlin.ourmemories.view.splash.presenter

import android.os.Handler
import com.kotlin.ourmemories.data.source.profile.ProfileRepository
import com.kotlin.ourmemories.view.splash.SplashActivity


/**
 * Created by kimmingyu on 2017. 11. 2..
 */
interface SplashContract {
    interface Presenter{
        val mHandler: Handler
        var activity: SplashActivity
        var profileData: ProfileRepository

        fun autoLogin()
        fun isLoginCheck()
    }
}