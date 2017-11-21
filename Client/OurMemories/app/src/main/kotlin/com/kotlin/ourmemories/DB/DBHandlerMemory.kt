package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        for(i in 1..20){
            var memoryData = MemoryData(0, "제목"+i,33.22,22.22,"나라"+i, 0,0,0)
        }

    }
}

class DBHandlerMemory(context:Context) : SQLiteOpenHelper(context, MemoryData.DB_NAME, null, MemoryData.DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
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
}