package com.kotlin.ourmemories.service.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.view.splash.SplashActivity

/**
 * Created by kimmingyu on 2017. 12. 3..
 */
class MyFirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService(){
    private var badgeCount:Int = 0
    init {
        PManager.init()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val title = remoteMessage?.data!!["title"]
        val message = remoteMessage?.data!!["message"]

        sendNotification(title, message)

        // 배지 등록
        setAlarmBadge()
    }

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

    /**
     * 알람이 오면 앱 아이콘에 뱃지를 달아 유저에게 알람이 왔다는 것을 알려준다
     *
     */
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