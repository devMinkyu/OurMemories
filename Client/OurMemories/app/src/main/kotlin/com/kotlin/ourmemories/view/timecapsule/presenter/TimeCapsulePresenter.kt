package com.kotlin.ourmemories.view.timecapsule.presenter


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import java.io.File
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
class TimeCapsulePresenter(context: Context) : TimeCapsuleContract.Presenter {
    companion object {
        val PICK_IMAGE: Int = 1010
        val PICK_VIDEO: Int = 1011
        val REQ_PERMISSON = 101
    }

    lateinit var path: String
    lateinit private var uploadFile: File
    lateinit override var activity: TimeCapsuleActivity
    lateinit override var mView: TimeCapsuleContract.View
    private val mContext = context
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 24
    private var mMinute = 24

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
        } else {
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
        mMonth = month + 1
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


    override fun currentAddress() {
    }

    // 알람 설정 하는 부분
    override fun alarmTimeCapsule() {
        val items = arrayOf(mContext.resources.getString(R.string.alarm_day_ago), mContext.resources.getString(R.string.alarm_half_ago),
                mContext.resources.getString(R.string.alarm_half_half_ago), mContext.resources.getString(R.string.alarm_hour_ago))
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        var calendar = Calendar.getInstance()
        alertDialogBuilder.setItems(items) { dialog, id ->
            when (id) {
                0 -> {
                    if (mDay == 1) {
                        // 나중에
                    } else {
                        alarmSetting(calendar, mYear, mMonth, mDay - 1, mHour, mMinute)
                    }
                    mView.updateAlarmView(items[0])
                }
                1 -> {
                    if (mHour < 12) {
                        // 나중에
                    } else {
                        alarmSetting(calendar, mYear, mMonth, mDay, mHour - 12, mMinute)
                    }
                    mView.updateAlarmView(items[1])
                }
                2 -> {
                    if (mHour < 6) {
                        // 나중에
                    } else {
                        alarmSetting(calendar, mYear, mMonth, mDay, mHour - 6, mMinute)
                    }
                    mView.updateAlarmView(items[2])
                }
                3 -> {
                    if (mHour < 1) {
                        // 나중에
                    } else {
                        alarmSetting(calendar, mYear, mMonth, mDay, mHour - 1, mMinute)
                    }
                    mView.updateAlarmView(items[3])
                }
            }
        }
        // 테스트 용
//        Log.d("hoho", calendar.get(Calendar.MONTH).toString())
//        alarmSetting(calendar, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 10, 22)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val intent = Intent("com.kotlin.ourmemories.ALARM_START")
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val mAlarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }

    private fun alarmSetting(calendar: Calendar, year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day - 1)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
    }


    override fun photoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun videoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            intent.type = "video/*"
            activity.startActivityForResult(intent, PICK_VIDEO)
        }
    }

    override fun cameraPhotoTimeCapsule() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, PICK_IMAGE)
    }

    override fun cameraVideoTimeCapsule() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        activity.startActivityForResult(intent, PICK_VIDEO)
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