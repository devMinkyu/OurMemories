package com.kotlin.ourmemories.data.source.autologin

import com.kotlin.ourmemories.view.splash.SplashActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 11. 3..
 */

// remote,local 로의 데이터를 사용하기 위해서 인터페이스 선언해주는 곳
interface AutoLoginSource {
    fun getProfile(userId:String, requestProfileCallback:Callback, activity: SplashActivity)
}