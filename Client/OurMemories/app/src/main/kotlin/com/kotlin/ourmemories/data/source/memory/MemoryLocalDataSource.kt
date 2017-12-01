package com.kotlin.ourmemories.data.source.memory

import android.content.Context
import android.database.Cursor
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import com.kotlin.ourmemories.view.memorypin.presenter.MemoryPinPresenter
import okhttp3.Callback
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
// 로컬 디비에 저장
object MemoryLocalDataSource : MemorySource {
    lateinit override var memoryPinPresenter: MemoryPinPresenter
    fun init(context: Context) {
        DBManagerMemory.init(context)
    }

    override fun memorySave(id: String, title: String, fromDate: String, toDate: String?, lat: Double, lon: Double, nation: String, text: String, uploadFile: File?, classification: Int, requestMemoryCallback: Callback?, activity: AppCompatActivity) {
        val memory = MemoryData(id, title, lat, lon, nation, fromDate, toDate, classification)
        DBManagerMemory.addMemory(memory)
        DBManagerMemory.close()
    }

    // 내부 디비에서 받아온 정보와 비교후 유저에게 존재하면 알려줘서 선택 후 서버로 보내게 한다
    override fun getMemory(classification: Int, isLocal: Boolean, lat: Double, lon: Double) {
        val currentLocation = Location("current")
        val cursorLocation = Location("cursor")

        when (classification) {
            0 -> { }
            1 -> {
                val cursor = DBManagerMemory.getMemoryClassificationWithCursor(classification)
                cursor.moveToFirst()
                if (cursor.count != 0) {
                    val items: ArrayList<Memory> = ArrayList()
                    val item = arrayOfNulls<Memory>(cursor.count)
                    for (i in 0 until cursor.count) {
                        val mLat = cursor.getDouble(2)
                        val mLog = cursor.getDouble(3)

                        currentLocation.latitude = lat
                        currentLocation.longitude = lon
                        cursorLocation.latitude = mLat
                        cursorLocation.longitude = mLog

                        val distance = currentLocation.distanceTo(cursorLocation)

                        if (distance.toInt() <= 100) {
                            item[i] = Memory(cursor.getString(0), cursor.getString(1))
                            items.add(item[i]!!)
                        }
                        cursor.moveToNext()
                    }
                    memoryPinPresenter.userChooseDialog(items)
                }
            }
        }
        memoryPinPresenter.userChooseDialog(null)
    }
}

// 데이터 클래스
data class Memory(val id: String, val title: String)

