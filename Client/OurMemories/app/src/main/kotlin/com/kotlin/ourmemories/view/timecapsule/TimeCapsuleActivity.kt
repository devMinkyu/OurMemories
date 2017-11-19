package com.kotlin.ourmemories.view.timecapsule

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Gravity
import android.widget.*
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsuleContract
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsulePresenter
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsulePresenter.Companion.REQ_PERMISSON
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import kotlinx.android.synthetic.main.activity_timecapsule.*
import java.io.File

class TimeCapsuleActivity : AppCompatActivity(), TimeCapsuleContract.View {
    private lateinit var presenter: TimeCapsuleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecapsule)

        presenter = TimeCapsulePresenter(this).apply {
            mView = this@TimeCapsuleActivity
            activity = this@TimeCapsuleActivity
        }

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
        timeCapsuleDateText.setOnClickListener { presenter.dateTimeCapsule() }
        // 시간 버튼 눌렀을 때
        timeCapsuleFromTime.setOnClickListener { presenter.fromTimeTimeCapsule() }
        timeCapsuleToTime.setOnClickListener { presenter.toTimeTimeCapsule() }
        timeCapsuleAllTime.setOnClickListener {
            updateFromTimeView(0, 0)
            updateToTimeView(23, 59)

        }

        // 텍스트 버튼 눌렀을 때 EditText 생성
        timeCapsuleText.setOnClickListener {
            timeCapsuleContents.removeAllViews()
            val paddingSize: Int = this.resources.getDimension(R.dimen.memory_5size).toInt()
            val timeCapsuleText = EditText(this)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            timeCapsuleText.layoutParams = params
            timeCapsuleText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
            timeCapsuleText.gravity = Gravity.TOP and Gravity.START
            timeCapsuleText.setLines(6)
            timeCapsuleText.background = this.resources.getDrawable(R.drawable.border)
            timeCapsuleText.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
            timeCapsuleContents.addView(timeCapsuleText)
        }
        timeCapsuleAlarm.setOnClickListener {
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

        timeCapsuleSave.setOnClickListener { }
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
            hourOfDay > 12 -> {
                timeCapsuleFromTime.setText(String.format(pmTimeFormat, hourOfDay - 12, minute))
            }
            hourOfDay == 12 -> {
                timeCapsuleFromTime.setText(String.format(pmTimeFormat, hourOfDay, minute))
            }
            hourOfDay == 24 -> {
                timeCapsuleFromTime.setText(String.format(pmTimeFormat, 23, 59))
            }
            hourOfDay < 12 -> {
                timeCapsuleFromTime.setText(String.format(amTimeFormat, hourOfDay, minute))
            }
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

    override fun updateAlarmView(alarmMessage:String) {
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
        val notGranted = kotlin.arrayOfNulls<String>(permissions.size)
        when (requestCode) {
            REQ_PERMISSON -> {
                var index = 0
                for (i in 0 until permissions.size) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                        if (!rationale) {
                            val dialogBuild = AlertDialog.Builder(this).setTitle(this.resources.getString(R.string.permission_setting)).setMessage(this.resources.getString(R.string.permission_message))
                                    .setCancelable(true).setPositiveButton(this.resources.getString(R.string.permission_button)) { dialog, whichButton ->
                                showSetting()
                            }
                            dialogBuild.create().show()
                            return
                        } else {
                            notGranted[index++] = permissions[i]
                        }
                    }
                }
                if (notGranted.isNotEmpty()) {
                    ActivityCompat.requestPermissions(this, notGranted, REQ_PERMISSON)
                }
            }
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


}
