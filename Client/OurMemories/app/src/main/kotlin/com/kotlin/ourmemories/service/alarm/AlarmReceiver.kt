package com.kotlin.ourmemories.service.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.manager.networkmanager.NManager
import okhttp3.*
import java.io.IOException

class AlarmReceiver : BroadcastReceiver() {
    private val requestAlarmCapsuleCallback:Callback = object :Callback{
        override fun onFailure(call: Call?, e: IOException?) {}
        override fun onResponse(call: Call?, response: Response?) {}
    }

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getStringExtra("_id")
        Log.d("hoho", id)
        // network 설정
        NManager.init()
        PManager.init()
        val client = NManager.getClinet()

        // post 방식
        var builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(context.resources.getString(R.string.server_domain))
        builder.port(context.resources.getString(R.string.port_number).toInt())
        builder.addPathSegment("alarm")


        // Body 설정
        val formBuilder = FormBody.Builder().add("userId", PManager.getUserId())
        formBuilder.add("_id", id)

        // RequestBody 설정(파일 설정 시 Multipart로 설정)
        val body: RequestBody = formBuilder.build()

        // Request 설정
        val request: Request = Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build()

        // 비동기 방식(enqueue)으로 Callback 구현
        client!!.newCall(request).enqueue(requestAlarmCapsuleCallback)
    }
}
