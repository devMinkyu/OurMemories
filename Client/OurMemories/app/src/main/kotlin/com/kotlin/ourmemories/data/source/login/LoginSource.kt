package com.kotlin.ourmemories.data.source.login

import com.kotlin.ourmemories.view.login.LoginActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 11. 4..
 */
interface LoginSource {
    fun loginServer(accessToken:String, token:String, requestloginCallback:Callback, activity: LoginActivity)
}