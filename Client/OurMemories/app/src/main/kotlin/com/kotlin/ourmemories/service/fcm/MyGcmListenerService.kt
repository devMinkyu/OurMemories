package com.kotlin.ourmemories.service.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.android.gms.gcm.GcmListenerService
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.view.splash.SplashActivity



/**
 * Created by kimmingyu on 2017. 11. 9..
 */
class MyGcmListenerService:GcmListenerService() {
    companion object {
        val TAG = "MyGcmListenerService"
    }
    private var badgeCount:Int = 0
    init {
        PManager.init()
    }

    /**
     * @param from SenderID 값을 받아온다.
     * @param data Set형태로 GCM으로 받은 데이터 payload이다.
     */
    override fun onMessageReceived(from: String?, data: Bundle?) {
        val title = data?.getString("TITLE")
        val message = data?.getString("MSG")

        // 데이터가 잘오는지 출
        Log.d(TAG, "From: " + from)
        Log.d(TAG, "Title: " + title)
        Log.d(TAG, "Message: " + message)

        // GCM으로 받은 메세지를 디바이스에 알려주는 sendNotification()을 호출한다.
        sendNotification(title, message)

        // 배지 등록
        setAlarmBadge()

    }

    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다. 알림을 위한 메소드
     *
     * @param title 메세지 제목
     * @param message 메세지 내용
     *
     * 알람 메세지를 클릭을 하면 인텐트에서 정해준 스플래쉬 화면으로 바로 넘어간다 FLAG_ACTIVITY_CLEAR_TOP-> 우선순위를 가장 높게해서 가장 먼저 간다
     */
    private fun sendNotification(title: String?, message: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0,notificationBuilder.build())
    }

    private fun setAlarmBadge() {
        val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE")

        //배지의 카운트를 공유저장소로부터 가져온다.//
        badgeCount = PManager.getBadgeNumber()
        badgeCount++

        intent.putExtra("bageCount", badgeCount)
        //패키지 이름과 클래그 이름설정.//

        //문자열로 대입 가능//
        intent.putExtra("badgeCountPackageName", applicationContext.packageName) //패키지 이름//
        //배지의 적용은 맨 처음 띄우는 화면을 기준으로 한다.//
        intent.putExtra("badgeCountClassName", SplashActivity::class.java.name) //맨 처음 띄우는 화면 이름//

        //변경된 값으로 다시 공유 저장소 값 초기화.//
        PManager.setBadgeNumber(badgeCount)

        sendBroadcast(intent)
    }
}