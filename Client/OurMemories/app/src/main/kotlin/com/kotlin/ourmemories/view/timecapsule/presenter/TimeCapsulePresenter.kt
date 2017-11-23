package com.kotlin.ourmemories.view.timecapsule.presenter


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.unit.InputVaildation
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import kotlinx.android.synthetic.main.activity_timecapsule.*
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

    lateinit override var activity: TimeCapsuleActivity
    lateinit override var mView: TimeCapsuleContract.View
    override var mGoogleApiClient: GoogleApiClient? = null

    private val mContext = context
    lateinit var path: String
    lateinit private var uploadFile: File
    private var mYear:Int
    private var mMonth :Int
    private var mDay:Int
    private var fromHour:Int
    private var fromMinute:Int
    private var toHour = 0
    private var toMinute = 0
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var nation = ""
    private val calendar = Calendar.getInstance()
    init {
        DBManagerMemory.init(mContext)
        NManager.init()
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        fromHour = calendar.get(Calendar.HOUR_OF_DAY)
        fromMinute = calendar.get(Calendar.MINUTE)
    }

    // 날짜 처리하는 함수
    override fun dateTimeCapsule() {
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
        val fromDialog = TimePickerDialog(mContext, fromTimeListener, fromHour, fromMinute, false)
        fromDialog.show()
    }

    private val fromTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        fromHour = hourOfDay
        fromMinute = minute
        mView.updateFromTimeView(hourOfDay, minute)
    }

    // 종료시간 처리하는 함수
    override fun toTimeTimeCapsule() {
        val toDialog = TimePickerDialog(mContext, toTimeListener, fromHour + 1, fromMinute, false)
        toDialog.show()
    }

    private val toTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        if (fromHour > hourOfDay) {
            print(mContext.resources.getString(R.string.time_error_message))
            return@OnTimeSetListener
        } else if (fromHour == hourOfDay) {
            if ((fromMinute > minute) or (fromMinute == minute)) {
                print(mContext.resources.getString(R.string.time_error_message))
                return@OnTimeSetListener
            }
        }
        toHour = hourOfDay
        toMinute = minute
        mView.updateToTimeView(hourOfDay, minute)

    }

    // GPS 부분
    @SuppressLint("MissingPermission")
    override fun currentAddress() {
        val locationManger = activity.getSystemService(LOCATION_SERVICE) as LocationManager
        val permission = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if ((ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                or (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, permission, REQ_PERMISSON_LOCATION)
        } else {
            if(!locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                activity.startActivity(intent)
            }else {
                // 나중에 해결
                val location: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                lat = location.latitude
                lon = location.longitude
                val address = Geocoder(mContext, Locale.KOREAN).getFromLocation(lat,lon,2)
                nation = address[0].countryName
                mView.updateAddressView(address[0].getAddressLine(0))
            }
        }
    }


    // 알람 설정 하는 부분
    override fun alarmTimeCapsule() {
        val items = arrayOf(mContext.resources.getString(R.string.alarm_day_ago), mContext.resources.getString(R.string.alarm_half_ago),
                mContext.resources.getString(R.string.alarm_half_half_ago), mContext.resources.getString(R.string.alarm_hour_ago))
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        alertDialogBuilder.setItems(items) { dialog, id ->
            when (id) {
                0 -> {
                    when {
                        (mDay == 1) and (mMonth == 0) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear - 1)
                                set(Calendar.MONTH, 11)
                                set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), fromHour, fromMinute, 0)
                            }
                        }
                        (mDay == 1) and (mMonth != 0) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth - 1)
                                set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), fromHour, fromMinute, 0)
                            }
                        }
                        (mDay != 1) -> {
                            calendar.set(mYear, mMonth, mDay - 1, fromHour, fromMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[0])
                }
                1 -> {
                    when {
                        (fromHour < 12) and (mMonth == 0) and (mDay == 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear - 1)
                                set(Calendar.MONTH, 11)
                                set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 12, fromMinute, 0)
                            }
                        }
                        (fromHour < 12) and (mMonth == 0) and (mDay != 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth, mDay-1, 24 + fromHour - 12, fromMinute, 0)
                            }
                        }
                        (fromHour < 12) and (mMonth != 0) and (mMonth == 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth - 1)
                                set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 12, fromMinute, 0)
                            }
                        }
                        (fromHour < 12) and (mMonth != 0) and (mMonth != 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth, mDay-1, 24 + fromHour - 12, fromMinute, 0)
                            }
                        }
                        (fromHour >= 12) -> {
                            calendar.set(mYear, mMonth, mDay, fromHour - 12, fromMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[1])
                }
                2 -> {
                    when {
                        (fromHour < 6) and (mMonth == 0) and (mDay == 1) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear - 1)
                                set(Calendar.MONTH, 11)
                                set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 6, fromMinute, 0)
                            }
                        }
                        (fromHour < 6) and (mMonth == 0) and (mDay != 1) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth, mDay-1, 24 + fromHour - 6, fromMinute, 0)
                            }
                        }
                        (fromHour < 6) and (mMonth != 0) and (mDay != 1) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth, mDay-1, 24 + fromHour - 6, fromMinute, 0)
                            }
                        }
                        (fromHour < 6) and (mMonth != 0) and (mDay == 1) -> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth-1)
                                set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 6, fromMinute, 0)
                            }
                        }
                        (fromHour >= 6) -> {
                            calendar.set(mYear, mMonth, mDay, fromHour - 6, fromMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[2])
                }
                3 -> {
                    when {
                        (fromHour < 1) and (mMonth == 0) and (mDay == 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear - 1)
                                set(Calendar.MONTH, 11)
                                set(mYear - 1, 11, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 1, fromMinute, 0)
                            }
                        }
                        (fromHour < 1) and (mMonth == 0) and (mDay != 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth, mDay-1, 24 + fromHour - 1, fromMinute, 0)
                            }
                        }
                        (fromHour < 1) and (mMonth != 0) and (mDay == 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth - 1)
                                set(mYear, mMonth - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 24 + fromHour - 1, fromMinute, 0)
                            }
                        }
                        (fromHour < 1) and (mMonth != 0) and (mDay != 1)-> {
                            calendar.run {
                                set(Calendar.YEAR, mYear)
                                set(Calendar.MONTH, mMonth)
                                set(mYear, mMonth , mDay-1, 24 + fromHour - 1, fromMinute, 0)
                            }
                        }
                        (fromHour >= 1) -> {
                            calendar.set(mYear, mMonth, mDay, fromHour - 1, fromMinute, 0)
                        }
                    }
                    mView.updateAlarmView(items[3])
                }
            }

        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }

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

    override fun saveMemory() {
        // 내용을 채웠는지 검사
        val inputValidation = InputVaildation(mContext)
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_title), activity.timeCapsuleTitleEditText, activity.timeCapsuleTitleLayoutText)) return
        if(!inputValidation.isInputDate(mContext.resources.getString(R.string.error_message_date), activity.timeCapsuleDateText, activity.timeCapsuleDateLayoutText)) return
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_start), activity.timeCapsuleFromTime, activity.timeCapsuleFromTimeLayoutText)) return
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_end), activity.timeCapsuleToTime, activity.timeCapsuleToTimeLayoutText)) return
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_location), activity.timeCapsuleLocation, activity.timeCapsuleLocationLayoutText)) return
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_alarm), activity.timeCapsuleAlarm, activity.timeCapsuleAlarmLayoutText)) return
        if(!inputValidation.isInputContents(mContext.resources.getString(R.string.error_message_contents), activity.timeCapsuleContents, activity.timeCapsuleContentsLayoutText)) return

        // DB의 저장 -> data 부분으로 이동
        val title = activity.timeCapsuleTitleEditText.text.toString()
        val amDBDATEFormat = activity.resources.getString(R.string.db_memory_am_format)
        val pmDBDATEFormat = activity.resources.getString(R.string.db_memory_pm_format)
        var fromDate = when {
            fromHour > 12 -> String.format(pmDBDATEFormat, mYear, mMonth, mDay, fromHour, fromMinute)
            fromHour == 12 -> String.format(pmDBDATEFormat, mYear, mMonth, mDay, fromHour, fromMinute)
            fromHour < 12 -> String.format(amDBDATEFormat, mYear, mMonth, mDay, fromHour, fromMinute)
            else -> "0"
        }
        var toDate = when {
            toHour > 12 -> String.format(pmDBDATEFormat, mYear, mMonth, mDay, toHour, toMinute)
            toHour == 12 -> String.format(pmDBDATEFormat, mYear, mMonth, mDay, toHour, toMinute)
            toHour == 24 -> String.format(pmDBDATEFormat, mYear, mMonth, mDay, 23, 59)
            toHour < 12 -> String.format(amDBDATEFormat, mYear, mMonth, mDay, toHour, toMinute)
            else -> "0"
        }
        Log.d("hoho", fromDate)
        val memory = MemoryData(0, title,lat,lon,nation,fromDate,toDate, 0)
        DBManagerMemory.addMemory(memory)
        DBManagerMemory.close()

        // 알람 설정
        val intent = Intent("com.kotlin.ourmemories.ALARM_START")
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val mAlarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
        Log.d("hoho", calendar.get(Calendar.MONTH).toString())
        Log.d("hoho", calendar.get(Calendar.HOUR_OF_DAY).toString())

//        // 테스트
//        val cursor:Cursor = DBManagerMemory.getMemoryAllWithCursor()
//        Log.d("hoho" , cursor.count.toString())
//        cursor.moveToFirst()
//        Log.d("hoho" , "${cursor.getString(1)} ${cursor.getString(2)} ${cursor.getString(3)} ${cursor.getDouble(4)} ${cursor.getDouble(5)} ${cursor.getString(6)} ${cursor.getInt(7)}")


        activity.finish()
    }

    private fun print(text: String) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show()
    }
}