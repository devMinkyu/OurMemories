package com.kotlin.ourmemories.view.timecapsule.presenter

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
interface TimeCapsuleContract {
    interface View{
        fun updateDateView(year:Int, monthOfYear:Int, dayOfMonth:Int)
        fun updateTimeView()
    }
    interface Presenter{
        var mView: View

        fun dateTimeCapsule()
        fun timeTimeCapsule()
        fun currentAddress()
        fun alarmTimeCapsule()
        fun photoTimeCapsule()
        fun videoTimeCapsule()
        fun textTimeCapsule()
        fun cameraCapsule()
    }
}