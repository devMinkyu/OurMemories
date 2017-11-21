package com.kotlin.ourmemories.DB

import android.provider.BaseColumns

/**
 * Created by nyoun_000 on 2017-11-14.
 */
class DBContract {

    abstract class FeedEntry: BaseColumns{
        companion object {
            //임시 타임캡슐 테이블 생성
            val TABLE_NAME = "timecapsule"
            val  _ID = "_id"
            val TITLE = "title"
            //지도상 표기될 x,y좌표
            val X_COORDINATE = "x_coordinate"
            val Y_COORDINATE = "y_coordinate"
            val NATION_NAME = "nation_name"
            //사진된 저장 경로
            val PIC_PATH = "pic_path"
            //타임캡슐 0, 후기 1
            val CLASSIFICATION = "classification"

        }
    }
    companion object {
        val SQL_CREATE_TIMECAPSULE_ENTRIES = "create table if not exists " +
                DBContract.FeedEntry.TABLE_NAME + " ( " + DBContract.FeedEntry._ID + " integer primary key, "
    }

}