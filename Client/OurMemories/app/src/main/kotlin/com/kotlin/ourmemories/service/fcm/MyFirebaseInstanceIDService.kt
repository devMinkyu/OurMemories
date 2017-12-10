package com.kotlin.ourmemories.service.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


/**
 * Created by kimmingyu on 2017. 12. 3..
 */
class MyFirebaseInstanceIDService: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
    }
}