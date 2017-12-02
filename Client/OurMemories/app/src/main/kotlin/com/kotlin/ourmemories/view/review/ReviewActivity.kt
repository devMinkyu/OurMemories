package com.kotlin.ourmemories.view.review

import android.content.Context
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
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.memorylist.MemoryMapFragment
import com.kotlin.ourmemories.view.review.presenter.ReviewContract
import com.kotlin.ourmemories.view.review.presenter.ReviewPresenter
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import kotlinx.android.synthetic.main.activity_review.*
import java.io.File
import java.util.*

class ReviewActivity : AppCompatActivity(), ReviewContract.View {
    private lateinit var presenter: ReviewContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        presenter = ReviewPresenter(this).apply {
            mView = this@ReviewActivity
            activity = this@ReviewActivity
            memoryData = MemoryRepository(this@ReviewActivity)
        }

        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        reviewTitleText.typeface = canaroExtraBold

        reviewBack.setOnClickListener {
            finish()
        }

        // 오늘 날짜 적용
        val calendar = Calendar.getInstance()
        val dataFormat = this.resources.getString(R.string.date_format)
        reviewDateText.text = String.format(dataFormat, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))

        // editText 를 눌러도 키보드가 안나오게 하기 위함
        reviewLocation.inputType = InputType.TYPE_NULL

        reviewLocation.setOnClickListener {
            hideKey()
            presenter.currentAddress()
        }

        // 사진 버튼 눌렀을 때
        reviewPhoto.setOnClickListener {
            reviewContents.removeAllViews()
            presenter.photoReview()
        }
        // 비디오 버튼 눌렀을 때
        reviewVideo.setOnClickListener {
            reviewContents.removeAllViews()
            presenter.videoReview()
        }
        // 카메라로 사진을 찍을려고 할 때
        reviewCameraPhoto.setOnClickListener {
            reviewContents.removeAllViews()
            presenter.cameraPhotoReview()
        }
        // 카메라로 동영상을 찍을려고 할 때
        reviewCameraVideo.setOnClickListener {
            reviewContents.removeAllViews()
            presenter.cameraVideoReview()
        }

        reviewSave.setOnClickListener {
            presenter.saveMemory()
        }
    }

    override fun onStart() {
        super.onStart()
        if (presenter.mGoogleApiClient == null) {
            presenter.mGoogleApiClient = GoogleApiClient.Builder(applicationContext).addApi(LocationServices.API).build()
        }
        presenter.mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        if (presenter.mGoogleApiClient != null) {
            presenter.mGoogleApiClient!!.disconnect()
            presenter.mGoogleApiClient = null
        }
        super.onStop()
    }

    override fun updateAddressView(address: String) {
        reviewMapRoot.visibility = View.VISIBLE
        val mapFragment = supportFragmentManager.findFragmentById(R.id.reviewMap) as MemoryMapFragment
        mapFragment.getMapAsync(mapFragment)
        reviewLocation.setText(address)
    }

    // 사진을 받아서 contents 에 사진을 추가
    override fun updatePhotoView(uploadFile: File) {
        val reviewPhoto = ImageView(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        reviewPhoto.layoutParams = params
        reviewPhoto.scaleType = ImageView.ScaleType.FIT_START
        reviewPhoto.adjustViewBounds = true

        NManager.init()
        val picasso = NManager.gatPicasso()
        picasso!!.load(uploadFile)
                .transform(CropSquareTransformation())
                .into(reviewPhoto)
        reviewContents.addView(reviewPhoto)
    }

    // 동영상을 받아서 contents 에 동영상 추가
    override fun updateVideoView(uploadFile: File) {
        val reviewVideo = VideoView(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.resources.getDimension(R.dimen.video_height).toInt())
        reviewVideo.layoutParams = params
        val controller = MediaController(this)
        reviewVideo.setMediaController(controller)

        reviewVideo.setVideoPath(uploadFile.toString())
        reviewVideo.requestFocus()
        reviewContents.addView(reviewVideo)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        for (i in 0 until permissions.size) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                if (rationale) {
                    val dialogBuild = AlertDialog.Builder(this).setTitle(this.resources.getString(R.string.permission_setting)).setMessage(this.resources.getString(R.string.permission_message))
                            .setCancelable(true).setPositiveButton(this.resources.getString(R.string.permission_button)) { dialog, whichButton ->
                        showSetting()
                    }
                    dialogBuild.create().show()
                    return
                }
            }
        }
        when (requestCode) {
            ReviewPresenter.REQ_PERMISSON_IMAGE_PHOTO -> {
                presenter.photoReview()
            }
            ReviewPresenter.REQ_PERMISSON_IMAGE_VIDEO -> {
                presenter.videoReview()
            }
            ReviewPresenter.REQ_PERMISSON_CAMERA_PHOTO -> {
                presenter.cameraPhotoReview()
            }
            ReviewPresenter.REQ_PERMISSON_CAMERA_VIDEO -> {
                presenter.cameraVideoReview()
            }
            ReviewPresenter.REQ_PERMISSON_LOCATION -> {
                presenter.currentAddress()
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
            ReviewPresenter.PICK_IMAGE -> {
                presenter.getImage(data)
            }
            ReviewPresenter.PICK_VIDEO -> {
                presenter.getVideo(data)
            }
        }
    }

    fun showDialog() { reviewLoading.visibility = View.VISIBLE }
    fun hideDialog() { reviewLoading.visibility = View.INVISIBLE }
    fun hideKey(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(reviewTitleEditText.windowToken, 0)
    }
}
