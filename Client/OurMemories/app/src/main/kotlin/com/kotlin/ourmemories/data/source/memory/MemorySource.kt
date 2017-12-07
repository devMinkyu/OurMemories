package com.kotlin.ourmemories.data.source.memory

import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.view.memorypin.presenter.MemoryPinPresenter
import okhttp3.Callback
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
interface MemorySource {
    var memoryPinPresenter:MemoryPinPresenter
    fun memorySave(id:String, title:String, fromDate:String, toDate:String?, lat:Double, lon:Double, address:String, nation:String, text:String, uploadFile:File? ,classification:Int, requestMemoryCallback: Callback?, activity: AppCompatActivity)
    fun getLocalMemory(classification: Int, lat:Double, lon:Double)
    fun getRemoteMemory(id:String, requestMemoryCallback: Callback, activity: AppCompatActivity)
}