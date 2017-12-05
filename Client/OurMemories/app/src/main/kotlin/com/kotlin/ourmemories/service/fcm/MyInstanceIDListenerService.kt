package com.kotlin.ourmemories.service.fcm

import android.content.Intent
import com.google.android.gms.iid.InstanceIDListenerService

/**
 * Created by kimmingyu on 2017. 11. 9..
 */
class MyInstanceIDListenerService: InstanceIDListenerService() {
    override fun onTokenRefresh() {
        startService(Intent(this, RegistrationIntentService::class.java))
    }
}