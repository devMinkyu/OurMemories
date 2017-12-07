package com.kotlin.ourmemories.data.source.login

import android.util.Log
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.login.LoginActivity
import okhttp3.*

/**
 * Created by kimmingyu on 2017. 11. 4..
 */
object LoginRemoteDataSource: LoginSource {
    override fun loginServer(accessToken:String, token:String, requestloginCallback: Callback, activity: LoginActivity) {
            // network 설정
            NManager.init()
            val client = NManager.getClinet()

            // post 방식
            var builder = HttpUrl.Builder()

            builder.scheme("http")
            builder.host(activity.resources.getString(R.string.server_domain))
            builder.port(activity.resources.getString(R.string.port_number).toInt())
            builder.addPathSegment("facebookLogin")

            // Body 설정
            val formBuilder = FormBody.Builder().add("accessToken", accessToken)
            formBuilder.add("userId", accessToken)
            formBuilder.add("token", token)

            // RequestBody 설정(파일 설정 시 Multipart로 설정)
            val body: RequestBody = formBuilder.build()

            // Request 설정
            val request: Request = Request.Builder()
                    .url(builder.build())
                    .post(body)
                    .tag(activity)
                    .build()

        // 비동기 방식(enqueue)으로 Callback 구현
        client!!.newCall(request).enqueue(requestloginCallback)
    }
}