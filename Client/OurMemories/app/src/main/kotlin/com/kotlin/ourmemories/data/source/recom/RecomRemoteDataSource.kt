package com.kotlin.ourmemories.data.source.recom

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.recom.RecomActivity
import okhttp3.*

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
object RecomRemoteDataSource:RecomSource {
    override fun getRemoteReview(siDo: String, siGunGu: String, requestRecomCallback: Callback, activity: RecomActivity) {
        NManager.init()
        val client = NManager.getClinet()

        // POST방식의 프로토콜 사용
        var builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(activity.resources.getString(R.string.server_domain))
        builder.port(activity.resources.getString(R.string.port_number).toInt())
        builder.addPathSegment("recom")
        builder.addPathSegment("review")

        // Body 설정
        val formBuilder = FormBody.Builder().add("siDo", siDo)
        formBuilder.add("siGunGu", siGunGu)

        // RequestBody 설정(파일 설정 시 Multipart로 설정)
        val body: RequestBody = formBuilder.build()


        // Request 설정
        val request: Request = Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(activity)
                .build()

        // 비동기 방식(enqueue)으로 Callback 구현
        client!!.newCall(request).enqueue(requestRecomCallback)
    }
}