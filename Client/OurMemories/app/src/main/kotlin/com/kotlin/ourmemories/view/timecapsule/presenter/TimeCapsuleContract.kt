package com.kotlin.ourmemories.view.timecapsule.presenter

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
interface TimeCapsuleContract {
    interface View{
        fun updateDateView(year:Int, monthOfYear:Int, dayOfMonth:Int)
        fun updateFromTimeView(hourOfDay:Int , minute:Int)
        fun updateToTimeView(hourOfDay:Int , minute:Int)
    }
    interface Presenter{
        var mView: View

        fun dateTimeCapsule()

        fun fromTimeTimeCapsule()
        fun toTimeTimeCapsule()

        fun currentAddress()
        fun alarmTimeCapsule()

        fun photoTimeCapsule()
        fun videoTimeCapsule()
        fun textTimeCapsule()
        fun cameraCapsule()
    }
}