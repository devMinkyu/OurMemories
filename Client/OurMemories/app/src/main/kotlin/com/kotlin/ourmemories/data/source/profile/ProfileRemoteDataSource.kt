package com.kotlin.ourmemories.data.source.profile

import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.splash.SplashActivity
import okhttp3.*

/**
 * Created by kimmingyu on 2017. 11. 3..
 */
// 서버로 접속하여 데이터를 가져오고 callback변수로 반환해주는 곳
object ProfileRemoteDataSource:ProfileSource {
    override fun getProfile(userId: String, requestProfileCallback: Callback, activity: SplashActivity) {
        // 네트워크 설정
        NManager.init()

        val client = NManager.getClinet()

        // POST방식의 프로토콜 사용
        var builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(activity.resources.getString(R.string.server_domain))
        builder.port(8000)
        builder.addPathSegment("profile")


        // Body 설정
        val formBuilder = FormBody.Builder().add("userId", userId)

        // RequestBody 설정(파일 설정 시 Multipart로 설정)
        val body: RequestBody = formBuilder.build()

        // Request 설정
        val request: Request = Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(activity)
                .build()

        // 비동기 방식(enqueue)으로 Callback 구현
        client!!.newCall(request).enqueue(requestProfileCallback)
    }
}