package com.kotlin.ourmemories.data.source.recom

import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.view.recom.RecomActivity
import okhttp3.Callback

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
class RecomRepository:RecomSource {
    private val recomDataSource = RecomRemoteDataSource
    override fun getRemoteReview(siDo: String, siGunGu: String, requestRecomCallback: Callback, activity: RecomActivity) {
        recomDataSource.getRemoteReview(siDo,siGunGu,requestRecomCallback, activity)
    }
}