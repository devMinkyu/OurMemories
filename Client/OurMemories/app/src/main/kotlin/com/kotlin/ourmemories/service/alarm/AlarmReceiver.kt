package com.kotlin.ourmemories.service.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // FCM 으로 보내 양쪽에다가 다 메세지 뿌려주게 하기
        Toast.makeText(context, "오 오는데?", Toast.LENGTH_LONG).show()
    }
}
