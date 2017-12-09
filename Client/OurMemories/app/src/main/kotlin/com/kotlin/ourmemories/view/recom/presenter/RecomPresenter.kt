package com.kotlin.ourmemories.view.recom.presenter

import android.util.Log
import com.google.gson.Gson
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.jsondata.ReComMemory
import com.kotlin.ourmemories.data.source.recom.RecomRepository
import com.kotlin.ourmemories.view.recom.RecomActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.io.IOException

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
class RecomPresenter : RecomContract.Presenter {
    lateinit override var activity: RecomActivity
    lateinit override var memoryData: RecomRepository
    lateinit override var mView: RecomContract.View

    private val requestRecomCallback: Callback = object : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            activity.runOnUiThread {
                activity.hideDialog()
                activity.alert(activity.resources.getString(R.string.error_message_network), "Review") {
                    yesButton { activity.finish() }
                }.show()
            }
        }

        override fun onResponse(call: Call?, response: Response?) {
            val responseData = response?.body()!!.string()
            Log.d("hoho", responseData)
            val recomRequest: ReComMemory = Gson().fromJson(responseData, ReComMemory::class.java)

            val isSuccess = recomRequest.isSuccess
            if (isSuccess == "true") {
                activity.runOnUiThread {
                    activity.hideDialog()
                    mView.updateView(recomRequest.reviewMemoryResult!!)
                }
            } else if (isSuccess == "false") {
                activity.runOnUiThread {
                    activity.hideDialog()
                    activity.alert(activity.resources.getString(R.string.error_message_review), "Review") {
                        yesButton { activity.finish() }
                    }.show()
                }
            }
        }

    }

    override fun getReview(siDo: String, siGunGu: String) {
        activity.showDialog()
        memoryData.getRemoteReview(siDo, siGunGu, requestRecomCallback, activity)
    }
}