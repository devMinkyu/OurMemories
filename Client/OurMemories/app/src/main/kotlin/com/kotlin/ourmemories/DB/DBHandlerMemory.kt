package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kotlin.ourmemories.DB.MemoryData.MemoryTable.TABLE_NAME
import org.jetbrains.anko.db.*

/**
 * Created by nyoun_000 on 2017-11-14.
 */

// 싱글턴으로 구현
object DBManagerMemory {
    private var mDBHandler: DBHandlerMemory? = null

    fun init(context: Context) {
        if (mDBHandler == null) {
            mDBHandler = DBHandlerMemory(context)
            defaultAddTimeCapesule()
        }
    }

    val SQL_DELETE_TIMECAPSULE_ENTRIES = "drop table if exists " + TABLE_NAME
    fun deleteTable(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TIMECAPSULE_ENTRIES)
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

    // 추억 추가
    fun addMemory(memoryData: MemoryData) {
        val cv = ContentValues()
        cv.put(MemoryData.MemoryTable._ID, memoryData.id)
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
            mDBHandler?.writableDatabase?.delete(MemoryData.MemoryTable.TABLE_NAME, "_id", arrayOf(id))
        }
    }

    fun close() {
        mDBHandler?.close()
    }

    //    // 테스트용
    fun defaultAddTimeCapesule() {
        val cv = ContentValues()
        cv.put("title", "추억1")
        cv.put("latitude", 33.11)
        cv.put("longitude", 22.54)
        cv.put("nation_name", "나라1")
        cv.put(MemoryData.MemoryTable.FROM_DATE, "11")
        cv.put("to_date", "11")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억2")
        cv.put("latitude", 13.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라2")
        cv.put(MemoryData.MemoryTable.FROM_DATE, "12")
        cv.put("to_date", "12")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억3")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", "13")
        cv.put("to_date", "13")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억4")
        cv.put("latitude", 33.11)
        cv.put("longitude", 22.54)
        cv.put("nation_name", "나라1")
        cv.put("from_date", "14")
        cv.put("to_date", "14")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억5")
        cv.put("latitude", 13.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라2")
        cv.put("from_date", "15")
        cv.put("to_date", "15")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억6")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", "16")
        cv.put("to_date", "16")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억7")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", "17")
        cv.put("to_date", "17")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억8")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", "18")
        cv.put("to_date", "18")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

        cv.put("title", "추억9")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", "19")
        cv.put("to_date", "19")
        cv.put("classification", 0)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(TABLE_NAME, null, cv)
        }

    }
}

class DBHandlerMemory(context: Context) : SQLiteOpenHelper(context, MemoryData.DB_NAME, null, MemoryData.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.dropTable(MemoryData.MemoryTable.TABLE_NAME, true)

        db?.createTable(MemoryData.MemoryTable.TABLE_NAME, true,
                Pair(MemoryData.MemoryTable._ID, TEXT + PRIMARY_KEY),
                Pair(MemoryData.MemoryTable.TITLE, TEXT),
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