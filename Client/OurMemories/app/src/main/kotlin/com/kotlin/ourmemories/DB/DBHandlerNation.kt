package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*

/**
 * Created by nyoun_000 on 2017-11-20.
 */
object DBManagerNation{
    private var mDBHandler: DBHandlerNation? = null

    fun init(context: Context){
        if(mDBHandler == null){
            mDBHandler = DBHandlerNation(context)
        }
    }

    val SQL_DELETE_TIMECAPSULE_ENTRIES = "drop table if exists " + NationData.NationTable.TABLE_NAME

    fun deleteTable(db:SQLiteDatabase){
        db.execSQL(SQL_DELETE_TIMECAPSULE_ENTRIES)
    }
    //모든 나라 데이터 겟
    fun getNationAllWithCursor():Cursor =
            mDBHandler?.readableDatabase!!.query(NationData.NationTable.TABLE_NAME,
                    arrayOf(NationData.NationTable._ID,
                            NationData.NationTable.NAME,
                            NationData.NationTable.LATITUDE,
                            NationData.NationTable.LONGITUDE),
                    null,null,null,null, NationData.NationTable._ID)

    //나라추가
    fun addNation(nationData: NationData){
        val cv = ContentValues()
        cv.put(NationData.NationTable.NAME, nationData.name)
        cv.put(NationData.NationTable.LATITUDE, nationData.latitude)
        cv.put(NationData.NationTable.LONGITUDE, nationData.longitude)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)
        }
    }

    fun deleteNation(id: Long){
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.delete(NationData.NationTable.TABLE_NAME, "_id", arrayOf(id.toString()))
        }
    }

    fun close(){
        mDBHandler?.close()
    }

    fun defaultAddNation(){
        val cv = ContentValues()
        cv.put("name", "대한민국")
        cv.put("latitude", 33.11)
        cv.put("longitude", 22.54)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)

        cv.put("name", "미국")
        cv.put("latitude", 21.00)
        cv.put("longitude", 10.47)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)

        cv.put("name", "일본")
        cv.put("latitude", 34.11)
        cv.put("longitude", 10.54)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)

        cv.put("name", "대한민국2")
        cv.put("latitude", 33.11)
        cv.put("longitude", 22.54)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)

        cv.put("name", "미국2")
        cv.put("latitude", 21.00)
        cv.put("longitude", 10.47)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)

        cv.put("name", "일본2")
        cv.put("latitude", 34.11)
        cv.put("longitude", 10.54)
        mDBHandler?.writableDatabase?.insert(NationData.NationTable.TABLE_NAME, null, cv)
    }

}

class DBHandlerNation(context: Context) : SQLiteOpenHelper(context, NationData.DB_NAME, null, NationData.DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.dropTable(NationData.NationTable.TABLE_NAME, true)
        db?.createTable(NationData.NationTable.TABLE_NAME, true,
                Pair(NationData.NationTable._ID, INTEGER + PRIMARY_KEY),
                Pair(NationData.NationTable.NAME, TEXT),
                Pair(NationData.NationTable.LATITUDE, REAL),
                Pair(NationData.NationTable.LONGITUDE, REAL)
                )
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}