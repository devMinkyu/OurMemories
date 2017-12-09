package com.kotlin.ourmemories.service.alarm

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.media.RingtoneManager
import android.support.v7.app.NotificationCompat
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.splash.SplashActivity

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class GeofenceTransitionsIntentService : IntentService("GeofenceTransitionsIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        var event:GeofencingEvent = GeofencingEvent.fromIntent(intent)

        val triggeringGeofences:MutableList<Geofence> = event.triggeringGeofences

        val triggeringGeofencesIdList:ArrayList<String> = ArrayList()
        triggeringGeofences.forEach {
            triggeringGeofencesIdList.add(it.requestId)
        }
        val IDs = TextUtils.join(", ", triggeringGeofencesIdList)

        Log.d("hoho", "서비스 내부 $IDs")
        sendNotification("테스트")
        val tranitiontype = event.geofenceTransition
        if(tranitiontype == Geofence.GEOFENCE_TRANSITION_ENTER){
            Log.d("hoho", "들어옴 $IDs")
            sendNotification("들어옴")
        }
        if(tranitiontype == Geofence.GEOFENCE_TRANSITION_EXIT){
            Log.d("hoho", "나감 $IDs")
            sendNotification("나감")
        }
        if(tranitiontype == Geofence.GEOFENCE_TRANSITION_DWELL){
            Log.d("hoho", "뭐여 $IDs")
            sendNotification("머뭄")
        }

    }

    private fun sendNotification(message: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ourmemory_logo)
                .setContentTitle("OurMemories")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0,notificationBuilder.build())
    }

//    /**
//     * Handle action Foo in the provided background thread with the provided
//     * parameters.
//     */
//    private fun handleActionFoo(param1: String, param2: String) {
//        // TODO: Handle action Foo
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//    /**
//     * Handle action Baz in the provided background thread with the provided
//     * parameters.
//     */
//    private fun handleActionBaz(param1: String, param2: String) {
//        // TODO: Handle action Baz
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//    companion object {
//        // TODO: Rename actions, choose action names that describe tasks that this
//        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//        private val ACTION_FOO = "com.kotlin.ourmemories.action.FOO"
//        private val ACTION_BAZ = "com.kotlin.ourmemories.action.BAZ"
//
//        // TODO: Rename parameters
//        private val EXTRA_PARAM1 = "com.kotlin.ourmemories.extra.PARAM1"
//        private val EXTRA_PARAM2 = "com.kotlin.ourmemories.extra.PARAM2"
//
//        /**
//         * Starts this service to perform action Foo with the given parameters. If
//         * the service is already performing a task this action will be queued.
//         *
//         * @see IntentService
//         */
//        // TODO: Customize helper method
//        fun startActionFoo(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
//            intent.action = ACTION_FOO
//            intent.putExtra(EXTRA_PARAM1, param1)
//            intent.putExtra(EXTRA_PARAM2, param2)
//            context.startService(intent)
//        }
//
//        /**
//         * Starts this service to perform action Baz with the given parameters. If
//         * the service is already performing a task this action will be queued.
//         *
//         * @see IntentService
//         */
//        // TODO: Customize helper method
//        fun startActionBaz(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
//            intent.action = ACTION_BAZ
//            intent.putExtra(EXTRA_PARAM1, param1)
//            intent.putExtra(EXTRA_PARAM2, param2)
//            context.startService(intent)
//        }
//    }
}
