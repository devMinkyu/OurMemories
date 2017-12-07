package com.kotlin.ourmemories.data.source.login

import com.kotlin.ourmemories.view.login.LoginActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 11. 4..
 */
interface LoginSource {
    fun facebookLoginServer(accessToken:String, token:String, requestloginCallback:Callback, activity: LoginActivity)
    fun kakaoLoginServer(accessToken:String, token:String, requestloginCallback:Callback, activity: LoginActivity)
}