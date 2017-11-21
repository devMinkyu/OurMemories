package com.kotlin.ourmemories.DB

/**
 * Created by nyoun_000 on 2017-11-20.
 */
data class NationData(val id:Long = 0,
                      val name:String = "No Name",
                      val latitude: Double = 0.0,
                      val longitude: Double = 0.0) {

    companion object {
        val DB_NAME = "OurMemory.db"
        val DB_VERSION = 1
    }
    //메모리 테이블 테스트
    object NationTable{
        val TABLE_NAME = "nation"
        val  _ID = "_id"
        val NAME = "name"
        val LATITUDE = "latitude" //x좌표
        val LONGITUDE = "longitude" //y좌표
    }
}
