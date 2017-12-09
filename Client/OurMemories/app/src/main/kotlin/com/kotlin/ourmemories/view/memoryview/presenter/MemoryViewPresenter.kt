package com.kotlin.ourmemories.view.memoryview.presenter

import android.content.Context
import com.google.gson.Gson
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.jsondata.MemoryItem
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memoryview.MemoryViewActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.io.IOException

/**
 * Created by kimmingyu on 2017. 12. 2..
 */
class MemoryViewPresenter : MemoryViewContract.Presenter {
    lateinit override var mView: MemoryViewContract.View
    lateinit override var activity: MemoryViewActivity
    lateinit override var memoryData: MemoryRepository

    private val requestViewCallback: Callback = object : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            activity.runOnUiThread {
                activity.hideDialog()
                activity.alert(activity.resources.getString(R.string.error_message_network), "MemoryView") {
                    yesButton { activity.finish() }
                }.show()
            }
        }

        override fun onResponse(call: Call?, response: Response?) {
            val responseData = response?.body()!!.string()
            val memoryItemRequest: MemoryItem = Gson().fromJson(responseData, MemoryItem::class.java)
            val isSuccess = memoryItemRequest.isSuccess
            if (isSuccess == "true") {
                activity.runOnUiThread {
                    mView.updateView(memoryItemRequest.memoryItemResult.mediaMemory, memoryItemRequest.memoryItemResult.textMemory)
                }
            } else if (isSuccess == "false") {
                activity.runOnUiThread {
                    activity.alert(activity.resources.getString(R.string.error_message_network), "MemoryView") {
                        yesButton { activity.finish() }
                    }.show()
                }
            }

        }
    }

    override fun takeMemory(id: String) {
        memoryData.getRemoteMemory(id, requestViewCallback, activity)
    }
}