package com.kotlin.ourmemories.view.timecapsule.presenter


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import org.jetbrains.anko.toast
import java.io.File
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
class TimeCapsulePresenter(context: Context) : TimeCapsuleContract.Presenter {
    companion object {
        val PICK_IMAGE: Int = 1010
        val PICK_VIDEO: Int = 1011
        val REQ_PERMISSON_IMAGE_PHOTO = 101
        val REQ_PERMISSON_IMAGE_VIDEO = 102
        val REQ_PERMISSON_CAMERA_PHOTO = 103
        val REQ_PERMISSON_CAMERA_VIDEO = 104
        val REQ_PERMISSON_LOCATION = 105
    }

    lateinit var path: String
    lateinit private var uploadFile: File
    lateinit override var activity: TimeCapsuleActivity
    lateinit override var mView: TimeCapsuleContract.View
    override var mGoogleApiClient: GoogleApiClient? = null

    private val mContext = context
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 24
    private var mMinute = 24
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    // 날짜 처리하는 함수
    override fun dateTimeCapsule() {
        val calendar = Calendar.getInstance()
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(mContext, dateListener, mYear, mMonth, mDay).show()
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        if (mYear > year) {
            print(mContext.resources.getString(R.string.year_error_message))
            return@OnDateSetListener
        } else if (mYear == year) {
            if (mMonth > month) {
                print(mContext.resources.getString(R.string.month_error_message))
                return@OnDateSetListener
            } else if (mMonth == month) {
                if (mDay > dayOfMonth) {
                    print(mContext.resources.getString(R.string.day_error_message))
                    return@OnDateSetListener
                } else if (mDay == dayOfMonth) {
                    print(mContext.resources.getString(R.string.same_day_error_message))
                    return@OnDateSetListener
                }
            }
        }
        mYear = year
        mMonth = month
        mDay = dayOfMonth
        mView.updateDateView(year, month + 1, dayOfMonth)
    }

    // 시작시간 처리하는 함수
    override fun fromTimeTimeCapsule() {
        val calendar = Calendar.getInstance()
        val fromDialog = TimePickerDialog(mContext, fromTimeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
        fromDialog.show()
    }

    private val fromTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        mHour = hourOfDay
        mMinute = minute
        mView.updateFromTimeView(hourOfDay, minute)
    }

    // 종료시간 처리하는 함수
    override fun toTimeTimeCapsule() {
        val toDialog = TimePickerDialog(mContext, toTimeListener, mHour + 1, mMinute, false)
        toDialog.show()
    }

    private val toTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        if (mHour > hourOfDay) {
            print(mContext.resources.getString(R.string.time_error_message))
            return@OnTimeSetListener
        } else if (mHour == hourOfDay) {
            if ((mMinute > minute) or (mMinute == minute)) {
                print(mContext.resources.getString(R.string.time_error_message))
                return@OnTimeSetListener
            }
        }
        mView.updateToTimeView(hourOfDay, minute)

    }

    // GPS 부분
    @SuppressLint("MissingPermission")
    override fun currentAddress() {
        val permission = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if ((ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                or (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, permission, REQ_PERMISSON_LOCATION)
        } else {
            val location: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
            if(location == null){
                activity.toast("GPS가 꺼져 있습니다")
            }else {
                lat = location.latitude
                lon = location.longitude
                activity.toast("$lat $lon")
                mView.updateAddressView(lat, lon)
            }
        }
    }


    // 알람 설정 하는 부분
    override fun alarmTimeCapsule() {
        val items = arrayOf(mContext.resources.getString(R.string.alarm_day_ago), mContext.resources.getString(R.string.alarm_half_ago),
                mContext.resources.getString(R.string.alarm_half_half_ago), mContext.resources.getString(R.string.alarm_hour_ago))
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        val calendar = Calendar.getInstance()
        alertDialogBuilder.setItems(items) { dialog, id ->
            when (id) {
                0 -> {
                    when {
                        (mDay == 1) and (mMonth == 0) -> {
                            calendar.set(Calendar.YEAR, mYear - 1)
                            calendar.set(Calendar.MONTH, 11)
                            calendar.set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), mHour, mMinute, 0)
                        }
                        (mDay == 1) and (mMonth != 0) -> {
                            calendar.set(Calendar.YEAR, mYear)
                            calendar.set(Calendar.MONTH, mMonth - 1)
                            calendar.set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), mHour, mMinute, 0)
                        }
                        (mDay != 1) -> {
                            calendar.set(mYear, mMonth, mDay - 1, mHour, mMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[0])
                }
                1 -> {
                    when {
                        (mHour < 12) and (mMonth == 0) -> {
                            calendar.set(Calendar.YEAR, mYear - 1)
                            calendar.set(Calendar.MONTH, 11)
                            calendar.set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 12, mMinute, 0)
                        }
                        (mHour < 12) and (mMonth != 0) -> {
                            calendar.set(Calendar.YEAR, mYear)
                            calendar.set(Calendar.MONTH, mMonth - 1)
                            calendar.set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 12, mMinute, 0)
                        }
                        (mHour >= 12) -> {
                            calendar.set(mYear, mMonth, mDay, mHour - 12, mMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[1])
                }
                2 -> {
                    when {
                        (mHour < 6) and (mMonth == 0) -> {
                            calendar.set(Calendar.YEAR, mYear - 1)
                            calendar.set(Calendar.MONTH, 11)
                            calendar.set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 6, mMinute, 0)
                        }
                        (mHour < 6) and (mMonth != 0) -> {
                            calendar.set(Calendar.YEAR, mYear)
                            calendar.set(Calendar.MONTH, mMonth - 1)
                            calendar.set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 6, mMinute, 0)
                        }
                        (mHour >= 6) -> {
                            calendar.set(mYear, mMonth, mDay, mHour - 6, mMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[2])
                }
                3 -> {
                    when {
                        (mHour < 1) and (mMonth == 0) -> {
                            calendar.set(Calendar.YEAR, mYear - 1)
                            calendar.set(Calendar.MONTH, 11)
                            calendar.set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 1, mMinute, 0)
                        }
                        (mHour < 1) and (mMonth != 0) -> {
                            calendar.set(Calendar.YEAR, mYear)
                            calendar.set(Calendar.MONTH, mMonth - 1)
                            calendar.set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + mHour - 1, mMinute, 0)
                        }
                        (mHour >= 1) -> {
                            calendar.set(mYear, mMonth, mDay, mHour - 1, mMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[3])
                }
            }

        }
        // 테스트 용
//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
//        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1)
//        Log.d("hoho", calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString())
//        calendar.set(mYear,mMonth-1,calendar.getActualMaximum(Calendar.DAY_OF_MONTH),mHour,mMinute,0)
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 13, 58,0)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        // 저장하기 버튼 눌렀을 때 만드는 함수로 간다
        val intent = Intent("com.kotlin.ourmemories.ALARM_START")
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val mAlarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }

    // request Code 해결 ㄱㄱ

    override fun photoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON_IMAGE_PHOTO)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun videoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON_IMAGE_VIDEO)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            intent.type = "video/*"
            activity.startActivityForResult(intent, PICK_VIDEO)
        }
    }

    override fun cameraPhotoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), REQ_PERMISSON_CAMERA_PHOTO)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun cameraVideoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), REQ_PERMISSON_CAMERA_VIDEO)
        } else {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            activity.startActivityForResult(intent, PICK_VIDEO)
        }
    }


    override fun getImage(data: Intent?) {
        val uri = data?.data
        uri ?: return
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.contentResolver.query(uri, projection, null, null, null)
        if (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))

            uploadFile = File(path)

            mView.updatePhotoView(uploadFile)

        }
        cursor.close()
    }

    override fun getVideo(data: Intent?) {
        val uri = data?.data
        uri ?: return
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = activity.contentResolver.query(uri, projection, null, null, null)
        if (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))

            uploadFile = File(path)

            mView.updateVideoView(uploadFile)
        }
        cursor.close()
    }

    private fun print(text: String) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show()
    }
}