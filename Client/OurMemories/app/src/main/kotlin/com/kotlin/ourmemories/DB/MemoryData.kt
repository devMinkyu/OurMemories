package com.kotlin.ourmemories.DB

/**
 * Created by kimmingyu on 2017. 11. 15..
 */
data class MemoryData(val id:String = "",
                      val title:String = "No Title",
                      val latitude: Double = 0.0,
                      val longitude: Double = 0.0,
                      val nationName: String = "No Nation",
                      val fromDate: String = "No Date",
                      val toDate: String? = "No Date",
                      val classification:Int = 0) {
    companion object {
        val DB_NAME = "OurMemory2.db"
        val DB_VERSION = 1
    }
    //메모리 테이블 테스트
    object MemoryTable{
        val TABLE_NAME = "timecapsule"
        val _ID = "_id"
        val TITLE = "title"
        val LATITUDE = "latitude" //x좌표
        val LONGITUDE = "longitude" //y좌표
        val NATION_NAME = "nation_name"
        val FROM_DATE = "from_date"
        val TO_DATE = "to_date"
        val CLASSIFICATION = "classification" //타임캡슐 0, 후기 1
    }
}
