package com.kotlin.ourmemories.data.source.memory

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.memorypin.presenter.MemoryPinPresenter
import okhttp3.Callback
import java.io.File
import java.util.*

@SuppressLint("StaticFieldLeak")
/**
 * Created by kimmingyu on 2017. 11. 24..
 */
// 로컬 디비에 저장
object MemoryLocalDataSource : MemorySource {
    lateinit override var memoryPinPresenter: MemoryPinPresenter
    lateinit var mContext:Context
    fun init(context: Context) {
        mContext = context
        DBManagerMemory.init(context)
    }

    override fun memorySave(id: String, title: String, fromDate: String, toDate: String?, lat: Double, lon: Double, nation: String, text: String, uploadFile: File?, classification: Int, requestMemoryCallback: Callback?, activity: AppCompatActivity) {
        val memory = MemoryData(id, title, lat, lon, nation, fromDate, toDate, classification)
        DBManagerMemory.addMemory(memory)
        DBManagerMemory.close()
    }

    // 내부 디비에서 받아온 정보와 비교후 유저에게 존재하면 알려줘서 선택 후 서버로 보내게 한다
    override fun getLocalMemory(classification: Int, lat: Double, lon: Double) {
        val items: ArrayList<Memory> = ArrayList()
        when (classification) {
            0 -> { // timeCapsule
                val calendar = Calendar.getInstance()
                val dBDateFormat = mContext.resources.getString(R.string.date_format)
                val date = String.format(dBDateFormat,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH))
                val cursor = DBManagerMemory.getMemoryDayWithCursor(date)
                cursor.moveToFirst()

                if(cursor.count != 0){
                    if(cursor.getInt(7) == classification) {
                        val amFormat = mContext.resources.getString(R.string.am_format)
                        val pmFormat = mContext.resources.getString(R.string.pm_format)

                        val item = arrayOfNulls<Memory>(cursor.count)
                        loop@ for (i in 0 until cursor.count) {
                            val distance = distanceResult(lat, lon, cursor)

                            if (distance.toInt() <= 100) {
                                val fromDate = cursor.getString(5)
                                val toDate = cursor.getString(6)
                                var fromHour: Int
                                var toHour: Int
                                var fromTime: Int
                                var toTime: Int
                                val currentTime = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE)
                                // 시작 시간에서 시간을 추출 하기 위한 액션들
                                if (fromDate.contains(amFormat)) {
                                    fromTime = (extractHour(amFormat, fromDate) * 60) + extractMinute(fromDate)
                                } else {
                                    fromHour = extractHour(pmFormat, fromDate)
                                    if (fromHour != 12) fromHour += 12
                                    fromTime = (fromHour * 60) + extractMinute(fromDate)
                                }
                                // 종료 시간에서 시간을 추출 하기 위한 액션들
                                if (toDate.contains(amFormat)) {
                                    toTime = (extractHour(amFormat, toDate) * 60) + extractMinute(toDate)
                                } else {
                                    toHour = extractHour(pmFormat, toDate)
                                    if (toHour != 12) toHour += 12
                                    toTime = (toHour * 60) + extractMinute(toDate)
                                }
                                // 추출하고 현재시간이랑 비교후 조건에 맞는 데이터만 추출한다
                                when {
                                    ((fromTime < currentTime) and (toTime > currentTime)) -> {
                                        item[i] = Memory("0", cursor.getString(1))
                                        //item[i] = Memory(cursor.getString(0), cursor.getString(1))
                                        items.add(item[i]!!)
                                    }
                                    else -> {
                                        continue@loop
                                    }
                                }
                            }
                            cursor.moveToNext()
                        }

                    }
                }
                cursor.close()
            }
            1 -> { // review
                val cursor = DBManagerMemory.getMemoryClassificationWithCursor(classification)
                cursor.moveToFirst()
                if (cursor.count != 0) {
                    val item = arrayOfNulls<Memory>(cursor.count)
                    for (i in 0 until cursor.count) {
                        val distance = distanceResult(lat,lon,cursor)

                        if (distance.toInt() <= 100) {
                            item[i] = Memory("0", cursor.getString(1))
                            //item[i] = Memory(cursor.getString(0), cursor.getString(1))
                            items.add(item[i]!!)
                        }
                        cursor.moveToNext()
                    }
                }
                cursor.close()
            }
        }
        if(items.size == 0){
            memoryPinPresenter.userChooseDialog(null)
            return
        }else {
            memoryPinPresenter.userChooseDialog(items)
            return
        }
    }

    override fun getRemoteMemory(id: String, requestMemoryCallback: Callback, activity: AppCompatActivity) {}
}

fun distanceResult(lat:Double, lon:Double, cursor: Cursor):Float{
    val currentLocation = Location("current")
    val cursorLocation = Location("cursor")
    val mLat = cursor.getDouble(2)
    val mLog = cursor.getDouble(3)

    currentLocation.latitude = lat
    currentLocation.longitude = lon
    cursorLocation.latitude = mLat
    cursorLocation.longitude = mLog

    return currentLocation.distanceTo(cursorLocation)
}

fun extractHour(format:String, date:String):Int{
    val index = date.indexOf(format)
    val index2 = date.indexOf(":")
    return date.substring(index+3, index2).toInt()
}
fun extractMinute(date:String):Int{
    val index = date.indexOf(":")
    return date.substring(index+1).toInt()
}

// 데이터 클래스
data class Memory(val id: String, val title: String)

