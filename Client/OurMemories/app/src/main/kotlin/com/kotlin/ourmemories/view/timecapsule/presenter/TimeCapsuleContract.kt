package com.kotlin.ourmemories.view.timecapsule.presenter

import android.content.Intent
import com.google.android.gms.common.api.GoogleApiClient
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
interface TimeCapsuleContract {
    interface View {
        fun updateDateView(year: Int, monthOfYear: Int, dayOfMonth: Int)
        fun updateFromTimeView(hourOfDay: Int, minute: Int)
        fun updateToTimeView(hourOfDay: Int, minute: Int)
        fun updateAddressView(address: String)
        fun updateAlarmView(alarmMessage: String)
        fun updatePhotoView(uploadFile: File)
        fun updateVideoView(uploadFile: File)
    }

    interface Presenter {
        var mView: View
        var activity: TimeCapsuleActivity
        var mGoogleApiClient: GoogleApiClient?

        fun dateTimeCapsule()

        fun fromTimeTimeCapsule()
        fun toTimeTimeCapsule()

        fun currentAddress()
        fun alarmTimeCapsule()

        fun photoTimeCapsule()
        fun videoTimeCapsule()
        fun cameraPhotoTimeCapsule()
        fun cameraVideoTimeCapsule()

        fun getImage(data: Intent?)
        fun getVideo(data: Intent?)

        fun saveMemory()

    }
}