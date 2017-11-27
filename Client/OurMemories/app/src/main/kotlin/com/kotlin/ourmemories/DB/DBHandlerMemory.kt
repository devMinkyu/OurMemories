package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by nyoun_000 on 2017-11-14.
 */

// 싱글턴으로 구현
object DBManagerMemory {
    private var mDBHandler: DBHandlerMemory? = null

    fun init(context: Context){
        if(mDBHandler == null){
            mDBHandler = DBHandlerMemory(context)
        }
    }
    val SQL_DELETE_TIMECAPSULE_ENTRIES = "drop table if exists " + MemoryData.MemoryTable.TABLE_NAME
    fun deleteTable(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TIMECAPSULE_ENTRIES)
    }

    fun getMemoryAllWithCursor(): Cursor =
            mDBHandler?.readableDatabase!!.query(MemoryData.MemoryTable.TABLE_NAME,
                    arrayOf(MemoryData.MemoryTable._ID,
                            MemoryData.MemoryTable.TITLE,
                            MemoryData.MemoryTable.LATITUDE,
                            MemoryData.MemoryTable.LONGITUDE,
                            MemoryData.MemoryTable.NATION_NAME,
                            MemoryData.MemoryTable.CLASSIFICATION),
            null, null, null, null, MemoryData.MemoryTable._ID)

    fun getMemoriesWithCursor(nationName: String) : Cursor =
            mDBHandler?.readableDatabase!!.query(MemoryData.MemoryTable.TABLE_NAME,
                    arrayOf(MemoryData.MemoryTable._ID,
                            MemoryData.MemoryTable.TITLE,
                            MemoryData.MemoryTable.LATITUDE,
                            MemoryData.MemoryTable.LONGITUDE,
                            MemoryData.MemoryTable.NATION_NAME,
                            MemoryData.MemoryTable.CLASSIFICATION),
                    MemoryData.MemoryTable.NATION_NAME+"=?", arrayOf(nationName), null, null, MemoryData.MemoryTable._ID)

    // 추억 추가
    fun addMemory(memoryData: MemoryData){
        val cv = ContentValues()
        cv.put(MemoryData.MemoryTable.TITLE, memoryData.title)
        cv.put(MemoryData.MemoryTable.LATITUDE, memoryData.latitude)
        cv.put(MemoryData.MemoryTable.LONGITUDE, memoryData.longitude)
        cv.put(MemoryData.MemoryTable.NATION_NAME, memoryData.nation_name)
        cv.put(MemoryData.MemoryTable.FROM_DATE, memoryData.from_date)
        cv.put(MemoryData.MemoryTable.TO_DATE, memoryData.to_date)
        cv.put(MemoryData.MemoryTable.CLASSIFICATION, memoryData.classification)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(MemoryData.MemoryTable.TABLE_NAME, null, cv)
        }
    }
    // 추억 삭제
    fun deleteMemory(id: Long){
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.delete(MemoryData.MemoryTable.TABLE_NAME, "_id" , arrayOf(id.toString()))
        }
    }
    fun close(){
        mDBHandler?.close()
    }

    // 테스트용
    fun defaultAddTimeCapesule() {

        var memoryData = MemoryData(0, "남산에서 타임캡슐",37.553902,126.980732,"대한민국", 0,0,0)
        addMemory(memoryData)
        memoryData = MemoryData(0, "이태원에서 타임캡슐",37.533924,126.993662,"대한민국", 0,0,0)
        addMemory(memoryData)
        memoryData = MemoryData(0, "부산 첫만남 장소 타임캡슐",35.153012,129.118680,"대한민국", 0,0,0)
        addMemory(memoryData)
        memoryData = MemoryData(0, "전주 타임캡슐",35.814836,127.153150,"대한민국", 0,0,0)
        addMemory(memoryData)
        memoryData = MemoryData(0, "속초 타임캡슐",38.190457,128.603384,"대한민국", 0,0,0)
        addMemory(memoryData)
        memoryData = MemoryData(0, "여의나루 데이트",37.528145,126.934022,"대한민국", 0,0,1)
        addMemory(memoryData)
    }

}

class DBHandlerMemory(context:Context) : ManagedSQLiteOpenHelper(context, MemoryData.DB_NAME, null, MemoryData.DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        //db?.dropTable(MemoryData.MemoryTable.TABLE_NAME, true)

        db?.createTable(MemoryData.MemoryTable.TABLE_NAME,true,
                Pair(MemoryData.MemoryTable._ID, INTEGER + PRIMARY_KEY),
                Pair(MemoryData.MemoryTable.TITLE, TEXT),
                Pair(MemoryData.MemoryTable.LATITUDE, REAL),
                Pair(MemoryData.MemoryTable.LONGITUDE, REAL),
                Pair(MemoryData.MemoryTable.NATION_NAME, TEXT),
                Pair(MemoryData.MemoryTable.FROM_DATE, INTEGER),
                Pair(MemoryData.MemoryTable.TO_DATE, INTEGER),
                Pair(MemoryData.MemoryTable.CLASSIFICATION, INTEGER))
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(MemoryData.MemoryTable.TABLE_NAME, true)

    }
}