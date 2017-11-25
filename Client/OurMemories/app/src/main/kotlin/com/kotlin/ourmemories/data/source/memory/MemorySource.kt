package com.kotlin.ourmemories.data.source.memory

import android.support.v7.app.AppCompatActivity
import okhttp3.Callback
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
interface MemorySource {
    fun memorySave(id:String, title:String, fromDate:String, toDate:String?, lat:Double, lon:Double, nation:String, text:String?, uploadFile:File? ,classification:Int, requestMemoryCallback: Callback?, activity: AppCompatActivity)
}