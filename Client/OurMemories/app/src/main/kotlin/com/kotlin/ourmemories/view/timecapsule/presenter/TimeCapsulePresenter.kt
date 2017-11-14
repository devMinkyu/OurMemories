package com.kotlin.ourmemories.view.timecapsule.presenter


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
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
class TimeCapsulePresenter(context: Context):TimeCapsuleContract.Presenter {
    companion object {
        val PICK_IMAGE : Int = 1010
        val REQ_PERMISSON = 1011
    }
    lateinit var path:String
    lateinit var uploadFile: File
    lateinit override var activity: TimeCapsuleActivity
    lateinit override var mView: TimeCapsuleContract.View
    private val mContext = context
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    // 날짜 처리하는 함수
    override fun dateTimeCapsule() {
        val calendar = Calendar.getInstance()
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(mContext,dateListener, mYear, mMonth, mDay).show()
    }
    private val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        if(mYear>year){
            print(mContext.resources.getString(R.string.year_error_message))
            return@OnDateSetListener
        }else {
            if (mMonth > month) {
                print(mContext.resources.getString(R.string.month_error_message))
                return@OnDateSetListener
            }else if(mMonth == month){
                if(mDay > dayOfMonth){
                    print(mContext.resources.getString(R.string.day_error_message))
                    return@OnDateSetListener
                }else if(mDay == dayOfMonth){
                    print(mContext.resources.getString(R.string.same_day_error_message))
                    return@OnDateSetListener
                }
            }
        }
        mView.updateDateView(year,month+1,dayOfMonth)
    }
    // 시작시간 처리하는 함수
    override fun fromTimeTimeCapsule() {
        val calendar = Calendar.getInstance()
        val fromDialog = TimePickerDialog(mContext, fromTimeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
        fromDialog.setTitle("시작 시간")
        fromDialog.show()
    }
    private val fromTimeListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
        mHour = hourOfDay
        mMinute = minute
        mView.updateFromTimeView(hourOfDay, minute)
    }
    // 종료시간 처리하는 함수
    override fun toTimeTimeCapsule() {
        val toDialog = TimePickerDialog(mContext, toTimeListener,mHour+1 , mMinute, false)
        toDialog.setTitle("종료 시간")
        toDialog.show()
    }
    private val toTimeListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
        if(mHour > hourOfDay){
            print(mContext.resources.getString(R.string.time_error_message))
            return@OnTimeSetListener
        }else if(mHour == hourOfDay){
            if((mMinute > minute) or (mMinute == minute)){
                print(mContext.resources.getString(R.string.time_error_message))
                return@OnTimeSetListener
            }
        }
        mView.updateToTimeView(hourOfDay, minute)
    }


    override fun currentAddress() {
    }

    override fun alarmTimeCapsule() {
    }



    override fun photoTimeCapsule() {
        val check = ActivityCompat.checkSelfPermission(mContext,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(check != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON)
        } else{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/*")
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun videoTimeCapsule() {
    }
    override fun cameraCapsule() {
    }

    override fun getImage(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            PICK_IMAGE->{
                val uri =data?.data
                uri?:return

                val projection = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = activity.contentResolver.query(uri, projection,null,null,null)
                if(cursor.moveToNext()){
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                    Log.d("hoho", path)

                    uploadFile = File(path)
                    NManager.init()

                    mView.updatePhotoTimeView(uploadFile)

                }
            }
        }
    }

    private fun print(text:String){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show()
    }
}