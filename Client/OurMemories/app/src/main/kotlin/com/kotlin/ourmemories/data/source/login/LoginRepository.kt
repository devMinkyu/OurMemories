package com.kotlin.ourmemories.data.source.login

import com.kotlin.ourmemories.view.login.LoginActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 11. 4..
 */
class LoginRepository: LoginSource {
    private val loginRemoteDataSource = LoginRemoteDataSource
    override fun facebookLoginServer(accessToken: String, token:String, requestloginCallback: Callback, activity: LoginActivity) {
        loginRemoteDataSource.facebookLoginServer(accessToken, token, requestloginCallback, activity)
    }

    override fun kakaoLoginServer(accessToken: String, token: String, requestloginCallback: Callback, activity: LoginActivity) {
        loginRemoteDataSource.kakaoLoginServer(accessToken, token, requestloginCallback, activity)
    }
}