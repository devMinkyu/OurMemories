package com.kotlin.ourmemories.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by nyoun_000 on 2017-11-14.
 */
class DBHandler(context:Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "OurMemory.db"
        val DB_VERSION = 1
    }

    //타임캡슐 테이블 테스트
    val TABLE_NAME = "timecapsule"
    val  _ID = "_id"
    val TITLE = "title"
    val LATITUDE = "latitude" //x좌표
    val LONGITUDE = "longitude" //y좌표
    val NATION_NAME = "nation_name"
    val FROM_DATE = "from_date"
    val TO_DATE = "to_date"
    val CLASSIFICATION = "classification" //타임캡슐 0, 후기 1

    val SQL_CREATE_TIMECAPSULE_ENTRIES = "create table if not exists " + TABLE_NAME + " ( " +
            _ID + " integer primary key, " + TITLE + " text, " + LATITUDE + " real, " + LONGITUDE + " real, " +
            NATION_NAME + " text, " +  FROM_DATE + " numeric, " + TO_DATE + " numeric, " + CLASSIFICATION + " integer)"
    val SQL_DELETE_TIMECAPSULE_ENTRIES = "drop table if exists " + TABLE_NAME



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TIMECAPSULE_ENTRIES)
        defaultAddTimeCapesule()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun deleteTable(db:SQLiteDatabase){
        db.execSQL(SQL_DELETE_TIMECAPSULE_ENTRIES)
    }
    fun getTimeCapsuleAllWithCursor() : Cursor {
        return readableDatabase.query(TABLE_NAME, arrayOf(_ID, TITLE, LATITUDE, LONGITUDE, NATION_NAME, CLASSIFICATION), null, null, null, null, _ID)
    }

    fun defaultAddTimeCapesule(){
        var cv = ContentValues()
        cv.put("title", "추억1")
        cv.put("latitude", 33.11)
        cv.put("longitude", 22.54)
        cv.put("nation_name", "나라1")
        cv.put("from_date", 11)
        cv.put("to_date", 11)
        cv.put("classification", 0)
        writableDatabase.insert(TABLE_NAME, null, cv)

        cv.put("title", "추억2")
        cv.put("latitude", 13.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라2")
        cv.put("from_date", 11)
        cv.put("to_date", 11)
        cv.put("classification", 0)
        writableDatabase.insert(TABLE_NAME, null, cv)

        cv.put("title", "추억3")
        cv.put("latitude", 53.11)
        cv.put("longitude", 32.54)
        cv.put("nation_name", "나라3")
        cv.put("from_date", 11)
        cv.put("to_date", 11)
        cv.put("classification", 0)
        writableDatabase.insert(TABLE_NAME, null, cv)

    }

}