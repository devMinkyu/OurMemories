package com.kotlin.ourmemories.view.review.presenter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.unit.InputVaildation
import com.kotlin.ourmemories.view.review.ReviewActivity
import kotlinx.android.synthetic.main.activity_review.*
import org.jetbrains.anko.toast
import java.io.File
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 19..
 */
class ReviewPresenter(context: Context) : ReviewContract.Presenter {

    companion object {
        val PICK_IMAGE: Int = 1010
        val PICK_VIDEO: Int = 1011
        val REQ_PERMISSON_IMAGE_PHOTO = 101
        val REQ_PERMISSON_IMAGE_VIDEO = 102
        val REQ_PERMISSON_CAMERA_PHOTO = 103
        val REQ_PERMISSON_CAMERA_VIDEO = 104
        val REQ_PERMISSON_LOCATION = 105
    }


    lateinit override var mView: ReviewContract.View
    lateinit override var activity: ReviewActivity
    override var mGoogleApiClient: GoogleApiClient? = null

    lateinit var path: String
    lateinit private var uploadFile: File

    private val mContext = context
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var nation = ""

    init {
        DBManagerMemory.init(mContext)
        NManager.init()
    }

    override fun currentAddress() {
        val locationManger = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val permission = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if ((ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                or (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, permission, REQ_PERMISSON_LOCATION)
        } else {
            if (!locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                activity.startActivity(intent)
            } else {
                // 다시
                val location: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                lat = location.latitude
                lon = location.longitude
                val address = Geocoder(mContext, Locale.KOREAN).getFromLocation(lat,lon,2)
                nation = address[0].countryName
                mView.updateAddressView(address[0].getAddressLine(0))
            }
        }
    }


    override fun photoReview() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON_IMAGE_PHOTO)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun videoReview() {

        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON_IMAGE_VIDEO)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            intent.type = "video/*"
            activity.startActivityForResult(intent, PICK_VIDEO)
        }
    }

    override fun cameraPhotoReview() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), REQ_PERMISSON_CAMERA_PHOTO)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun cameraVideoReview() {
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
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_title), activity.reviewTitleEditText, activity.reviewTitleLayoutText)) return
        if(!inputValidation.isInputFilled(mContext.resources.getString(R.string.error_message_location), activity.reviewLocation, activity.reviewLocationLayoutText)) return
        if(!inputValidation.isInputContents(mContext.resources.getString(R.string.error_message_contents), activity.reviewContents, activity.reviewContentsLayoutText)) return

        val date = activity.reviewDateText.text.toString()
        val title = activity.reviewTitleEditText.text.toString()


        val memory = MemoryData(0, title,lat,lon,nation,date,"", 1)
        DBManagerMemory.addMemory(memory)

        DBManagerMemory.close()

        activity.finish()
    }
}