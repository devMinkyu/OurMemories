package com.kotlin.ourmemories.view.review.presenter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import com.kotlin.ourmemories.view.review.ReviewActivity
import java.io.File

/**
 * Created by kimmingyu on 2017. 11. 19..
 */
class ReviewPresenter(context: Context):ReviewContract.Presenter {

    companion object {
        val PICK_IMAGE: Int = 1010
        val PICK_VIDEO: Int = 1011
        val REQ_PERMISSON = 101
    }

    lateinit var path: String
    lateinit private var uploadFile: File

    lateinit override var mView: ReviewContract.View
    lateinit override var activity: ReviewActivity

    private val mContext = context

    override fun currentAddress() {
    }

    override fun photoReview() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            activity.startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun videoReview() {
        val check = ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSON)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            intent.type = "video/*"
            activity.startActivityForResult(intent, PICK_VIDEO)
        }
    }

    override fun cameraPhotoReview() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, PICK_IMAGE)
    }

    override fun cameraVideoReview() {
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
}