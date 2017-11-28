package com.kotlin.ourmemories.data.source.memory

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import okhttp3.Callback
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
// 로컬 디비에 저장
object MemoryLocalDataSource:MemorySource {
    fun init(context: Context) {
        DBManagerMemory.init(context)
    }
    override fun memorySave(id:String, title: String, fromDate: String, toDate: String?, lat: Double, lon: Double, nation: String, text:String, uploadFile: File?, classification: Int, requestMemoryCallback: Callback?, activity: AppCompatActivity) {
        val memory = MemoryData(id, title,lat,lon,nation,fromDate,toDate, classification)
        DBManagerMemory.addMemory(memory)
        DBManagerMemory.close()
    }
}