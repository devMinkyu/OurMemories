package com.kotlin.ourmemories.data.source.memory

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.Callback
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
class MemoryRepository(context: Context):MemorySource {
    private val memoryRemoteDataSource = MemoryRemoteDataSource
    private val memoryLocalDataSource = MemoryLocalDataSource
    init {
        memoryLocalDataSource.init(context)
    }

    // 로컬이냐 서버냐 구분해주는 클래스
    override fun memorySave(id:String, title: String, fromDate: String, toDate: String?, lat: Double, lon: Double, nation: String, text:String, uploadFile: File?, classification: Int, requestMemoryCallback: Callback?, activity: AppCompatActivity) {
        when{
            // 로컬 디비 저장
            (uploadFile == null)->{
                memoryLocalDataSource.memorySave(id,title,fromDate,toDate,lat,lon,nation,text,uploadFile,classification, requestMemoryCallback, activity)
            }
            // 서버 디비 저장
            (uploadFile != null)->{
                memoryRemoteDataSource.memorySave(id, title,fromDate,toDate,lat,lon,nation,text,uploadFile,classification, requestMemoryCallback, activity)
            }
        }
    }
}