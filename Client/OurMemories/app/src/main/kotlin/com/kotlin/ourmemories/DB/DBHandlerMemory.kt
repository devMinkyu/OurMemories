package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kotlin.ourmemories.DB.MemoryData.MemoryTable.TABLE_NAME
import org.jetbrains.anko.db.*
import java.io.File

/**
 * Created by nyoun_000 on 2017-11-14.
 */

// 싱글턴으로 구현
object DBManagerMemory {
    private var mDBHandler: DBHandlerMemory? = null

    fun init(context: Context) {
        if (mDBHandler == null) {
            mDBHandler = DBHandlerMemory(context)
        }
    }
    fun deleteTable() {
        mDBHandler?.writableDatabase!!.dropTable(TABLE_NAME,true)
    }

    // 디비에 있는 내용 다 가져오기
    fun getMemoryAllWithCursor(): Cursor =
            mDBHandler?.readableDatabase!!.query(TABLE_NAME,
                    arrayOf(MemoryData.MemoryTable._ID,
                            MemoryData.MemoryTable.TITLE,
                            MemoryData.MemoryTable.LATITUDE,
                            MemoryData.MemoryTable.LONGITUDE,
                            MemoryData.MemoryTable.NATION_NAME,
                            MemoryData.MemoryTable.FROM_DATE,
                            MemoryData.MemoryTable.TO_DATE,
                            MemoryData.MemoryTable.CLASSIFICATION),
                    null, null, null, null, MemoryData.MemoryTable._ID)

    fun getMemoryDayWithCursor(day:String): Cursor =
            mDBHandler?.readableDatabase!!.query(TABLE_NAME,
                    arrayOf(MemoryData.MemoryTable._ID,
                            MemoryData.MemoryTable.TITLE,
                            MemoryData.MemoryTable.LATITUDE,
                            MemoryData.MemoryTable.LONGITUDE,
                            MemoryData.MemoryTable.NATION_NAME,
                            MemoryData.MemoryTable.FROM_DATE,
                            MemoryData.MemoryTable.TO_DATE,
                            MemoryData.MemoryTable.CLASSIFICATION),
                    "from_date Like ?", arrayOf(day+"%"), null, null, MemoryData.MemoryTable._ID)

    // 추억 추가
    fun addMemory(memoryData: MemoryData) {
        val cv = ContentValues()
        //cv.put(MemoryData.MemoryTable._ID, memoryData.id)
        cv.put(MemoryData.MemoryTable.TITLE, memoryData.title)
        cv.put(MemoryData.MemoryTable.LATITUDE, memoryData.latitude)
        cv.put(MemoryData.MemoryTable.LONGITUDE, memoryData.longitude)
        cv.put(MemoryData.MemoryTable.NATION_NAME, memoryData.nationName)
        cv.put(MemoryData.MemoryTable.FROM_DATE, memoryData.fromDate)
        cv.put(MemoryData.MemoryTable.TO_DATE, memoryData.toDate)
        cv.put(MemoryData.MemoryTable.CLASSIFICATION, memoryData.classification)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

    }

    // 추억 삭제
    fun deleteMemory(id: String) {
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.delete(MemoryData.MemoryTable.TABLE_NAME, "_id=?", arrayOf(id))
        }
    }

    fun close() {
        mDBHandler?.close()
    }
}

class DBHandlerMemory(context: Context) : SQLiteOpenHelper(context, MemoryData.DB_NAME, null, MemoryData.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable(MemoryData.MemoryTable.TABLE_NAME, true,
                Pair(MemoryData.MemoryTable._ID, TEXT + PRIMARY_KEY),
                Pair(MemoryData.MemoryTable.TITLE, TEXT + PRIMARY_KEY),
                Pair(MemoryData.MemoryTable.LATITUDE, REAL),
                Pair(MemoryData.MemoryTable.LONGITUDE, REAL),
                Pair(MemoryData.MemoryTable.NATION_NAME, TEXT),
                Pair(MemoryData.MemoryTable.FROM_DATE, TEXT),
                Pair(MemoryData.MemoryTable.TO_DATE, TEXT),
                Pair(MemoryData.MemoryTable.CLASSIFICATION, INTEGER))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }
}