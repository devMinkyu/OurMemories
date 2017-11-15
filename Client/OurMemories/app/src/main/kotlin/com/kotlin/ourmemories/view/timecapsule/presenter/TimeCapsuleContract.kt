package com.kotlin.ourmemories.view.timecapsule.presenter

import android.content.Intent
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
interface TimeCapsuleContract {
    interface View{
        fun updateDateView(year:Int, monthOfYear:Int, dayOfMonth:Int)
        fun updateFromTimeView(hourOfDay:Int , minute:Int)
        fun updateToTimeView(hourOfDay:Int , minute:Int)
        fun updatePhotoTimeView(uploadFile: File)
        fun updateVideoTimeView(uploadFile: File)
    }
    interface Presenter{
        var mView: View
        var activity:TimeCapsuleActivity

        fun dateTimeCapsule()

        fun fromTimeTimeCapsule()
        fun toTimeTimeCapsule()

        fun currentAddress()
        fun alarmTimeCapsule()

        fun photoTimeCapsule()
        fun videoTimeCapsule()
        fun cameraCapsule()

        fun getImage(data: Intent?)
        fun getVideo(data: Intent?)

    }
}