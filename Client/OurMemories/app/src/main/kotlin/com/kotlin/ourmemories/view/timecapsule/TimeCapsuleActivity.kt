package com.kotlin.ourmemories.view.timecapsule

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.memory.MemoryMapFragment
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsuleContract
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsulePresenter
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import kotlinx.android.synthetic.main.activity_timecapsule.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import java.io.File
import java.util.*


class TimeCapsuleActivity : AppCompatActivity(), TimeCapsuleContract.View {
    private lateinit var presenter: TimeCapsuleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecapsule)

        presenter = TimeCapsulePresenter(this).apply {
            mView = this@TimeCapsuleActivity
            activity = this@TimeCapsuleActivity
            memoryData = MemoryRepository(this@TimeCapsuleActivity)
        }


        updateFromTimeView(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE))
        // 폰트 변경
        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        timeCapsuleTitleText.typeface = canaroExtraBold

        //뒤로가기
        timeCapsuleBack.setOnClickListener {
            this.finish()
        }

        // editText 를 눌러도 키보드가 안나오게 하기 위함
        timeCapsuleFromTime.inputType = InputType.TYPE_NULL
        timeCapsuleToTime.inputType = InputType.TYPE_NULL
        timeCapsuleLocation.inputType = InputType.TYPE_NULL
        timeCapsuleAlarm.inputType = InputType.TYPE_NULL
        timeCapsuleLocation.inputType = InputType.TYPE_NULL

        // 날짜 버튼 눌렀을 때
        timeCapsuleDateText.setOnClickListener {
            hideKey()
            presenter.dateTimeCapsule()
        }
        // 시간 버튼 눌렀을 때
        timeCapsuleFromTime.setOnClickListener {
            hideKey()
            presenter.fromTimeTimeCapsule()
        }
        timeCapsuleToTime.setOnClickListener {
            hideKey()
            presenter.toTimeTimeCapsule()
        }

        // 위치
        timeCapsuleLocation.setOnClickListener {
            hideKey()
            presenter.currentAddress()
        }
        timeCapsuleAddress.setOnClickListener {
            hideKey()
            presenter.currentAddress()
        }
        //알람
        timeCapsuleAlarm.setOnClickListener {
            hideKey()
            presenter.alarmTimeCapsule()
        }
        timeCapsuleAlarmImage.setOnClickListener {
            hideKey()
            presenter.alarmTimeCapsule()
        }
        // 사진 버튼 눌렀을 때
        timeCapsulePhoto.setOnClickListener {
            timeCapsuleContents.removeAllViews()
            presenter.photoTimeCapsule()
        }
        // 비디오 버튼 눌렀을 때
        timeCapsuleVideo.setOnClickListener {
            timeCapsuleContents.removeAllViews()
            presenter.videoTimeCapsule()
        }
        // 카메라로 사진을 찍을려고 할 때
        timeCapsuleCameraPhoto.setOnClickListener {
            timeCapsuleContents.removeAllViews()
            presenter.cameraPhotoTimeCapsule()
        }
        // 카메라로 동영상을 찍을려고 할 때
        timeCapsuleCameraVideo.setOnClickListener {
            timeCapsuleContents.removeAllViews()
            presenter.cameraVideoTimeCapsule()
        }

        timeCapsuleSave.setOnClickListener {
            presenter.saveMemory()
        }

    }


    override fun onStop() {
        if (presenter.mGoogleApiClient != null) {
            presenter.mGoogleApiClient!!.disconnect()
            presenter.mGoogleApiClient = null
        }
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        if (presenter.mGoogleApiClient == null) {
            presenter.mGoogleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).build()
        }
        presenter.mGoogleApiClient!!.connect()
    }

    // 날짜 선택 뷰
    override fun updateDateView(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dataFormat = this.resources.getString(R.string.date_format)
        timeCapsuleDateText.text = String.format(dataFormat, year, monthOfYear, dayOfMonth)
    }


    // 시간 선택 뷰들
    override fun updateFromTimeView(hourOfDay: Int, minute: Int) {
        val amTimeFormat = this.resources.getString(R.string.am_time_format)
        val pmTimeFormat = this.resources.getString(R.string.pm_time_format)
        when {
            hourOfDay > 12 -> timeCapsuleFromTime.setText(String.format(pmTimeFormat, hourOfDay - 12, minute))
            hourOfDay == 12 -> timeCapsuleFromTime.setText(String.format(pmTimeFormat, hourOfDay, minute))
            hourOfDay == 24 -> timeCapsuleFromTime.setText(String.format(pmTimeFormat, 23, 59))
            hourOfDay < 12 -> timeCapsuleFromTime.setText(String.format(amTimeFormat, hourOfDay, minute))
        }
    }


    override fun updateToTimeView(hourOfDay: Int, minute: Int) {
        val amTimeFormat = this.resources.getString(R.string.am_time_format)
        val pmTimeFormat = this.resources.getString(R.string.pm_time_format)
        when {
            hourOfDay > 12 -> {
                timeCapsuleToTime.setText(String.format(pmTimeFormat, hourOfDay - 12, minute))
            }
            hourOfDay == 12 -> {
                timeCapsuleToTime.setText(String.format(pmTimeFormat, hourOfDay, minute))
            }
            hourOfDay == 24 -> {
                timeCapsuleToTime.setText(String.format(pmTimeFormat, 23, 59))
            }
            hourOfDay < 12 -> {
                timeCapsuleToTime.setText(String.format(amTimeFormat, hourOfDay, minute))
            }
        }
    }

    override fun updateAddressView(address: String) {
        timeCapsuleMapRoot.visibility = View.VISIBLE
        val mapFragment = supportFragmentManager.findFragmentById(R.id.timeCapsuleMap) as MemoryMapFragment
        mapFragment.getMapAsync(mapFragment)
        timeCapsuleLocation.setText(address)
    }

    override fun updateAlarmView(alarmMessage: String) {
        timeCapsuleAlarm.setText(alarmMessage)
    }

    // 사진을 받아서 contents 에 사진을 추가
    override fun updatePhotoView(uploadFile: File) {
        val timeCapsulePhoto = ImageView(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        timeCapsulePhoto.layoutParams = params
        timeCapsulePhoto.scaleType = ImageView.ScaleType.FIT_START
        timeCapsulePhoto.adjustViewBounds = true

        NManager.init()
        val picasso = NManager.gatPicasso()
        picasso!!.load(uploadFile)
                .transform(CropSquareTransformation())
                .into(timeCapsulePhoto)
        timeCapsuleContents.addView(timeCapsulePhoto)
    }

    // 동영상을 받아서 contents 에 동영상 추가
    override fun updateVideoView(uploadFile: File) {
        val timeCapsuleVideo = VideoView(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.resources.getDimension(R.dimen.video_height).toInt())
        timeCapsuleVideo.layoutParams = params
        val controller = MediaController(this)
        timeCapsuleVideo.setMediaController(controller)

        timeCapsuleVideo.setVideoPath(uploadFile.toString())
        timeCapsuleVideo.requestFocus()
        timeCapsuleContents.addView(timeCapsuleVideo)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        for (i in 0 until permissions.size) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                if (rationale) {
                    alert(this.resources.getString(R.string.permission_message), this.resources.getString(R.string.permission_setting)){
                        positiveButton(resources.getString(R.string.permission_button)){showSetting()}
                        noButton {  }
                    }.show()
                    return
                }
            }
        }
        when (requestCode) {
            TimeCapsulePresenter.REQ_PERMISSON_IMAGE_PHOTO -> presenter.photoTimeCapsule()
            TimeCapsulePresenter.REQ_PERMISSON_IMAGE_VIDEO -> presenter.videoTimeCapsule()
            TimeCapsulePresenter.REQ_PERMISSON_CAMERA_PHOTO -> presenter.cameraPhotoTimeCapsule()
            TimeCapsulePresenter.REQ_PERMISSON_CAMERA_VIDEO -> presenter.cameraVideoTimeCapsule()
            TimeCapsulePresenter.REQ_PERMISSON_LOCATION -> presenter.currentAddress()
        }
    }

    private fun showSetting() {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TimeCapsulePresenter.PICK_IMAGE -> {
                presenter.getImage(data)
            }
            TimeCapsulePresenter.PICK_VIDEO -> {
                presenter.getVideo(data)
            }
        }

    }

    fun showDialog() {
        timeCapsuleLoading.visibility = View.VISIBLE
    }

    fun hideDialog() {
        timeCapsuleLoading.visibility = View.INVISIBLE
    }

    fun hideKey() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(timeCapsuleTitleEditText.windowToken, 0)
    }

}
