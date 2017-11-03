package com.kotlin.ourmemories.data.source.profile

import com.kotlin.ourmemories.view.splash.SplashActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 11. 3..
 */

// 요청이 서버로인지 로컬로인지 구분해서 데이터를 불러주는 곳
class ProfileRepository: ProfileSource {
    private val profileRemoteDataSource = ProfileRemoteDataSource

    override fun getProfile(userId: String, requestProfileCallback: Callback, activity: SplashActivity) {
        profileRemoteDataSource.getProfile(userId,requestProfileCallback,activity)
    }
}