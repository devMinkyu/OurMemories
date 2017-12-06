package com.kotlin.ourmemories.data.source.recom

import android.support.v7.app.AppCompatActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
interface RecomSource {
    fun getRemoteReview(siDo:String, siGunGu:String, requestRecomCallback: Callback, activity: AppCompatActivity)
}