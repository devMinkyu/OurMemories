package com.kotlin.ourmemories.view.memoryview

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.memoryview.presenter.MemoryViewContract
import com.kotlin.ourmemories.view.memoryview.presenter.MemoryViewPresenter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import kotlinx.android.synthetic.main.activity_memory_view.*

class MemoryViewActivity : AppCompatActivity(), MemoryViewContract.View {
    private lateinit var presenter: MemoryViewContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_view)
        showDialog()
        val intent = intent
        val id = intent.getStringExtra("id")

        presenter = MemoryViewPresenter().apply {
            memoryData = MemoryRepository(this@MemoryViewActivity)
            activity = this@MemoryViewActivity
            mView = this@MemoryViewActivity
        }
        presenter.takeMemory(id)
    }

    override fun updateView(media: String, text: String) {
        hideDialog()
        when {
            media.contains("jpg") -> {
                val memoryViewPhoto = ImageView(this)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                memoryViewPhoto.layoutParams = params
                memoryViewPhoto.scaleType = ImageView.ScaleType.FIT_START
                memoryViewPhoto.adjustViewBounds = true

//                NManager.init()
//                val picasso = NManager.gatPicasso()

                Picasso.with(this)
                        .load(media)
                        .transform(CropSquareTransformation())
                        .into(memoryViewPhoto)
                memoryViewContents.addView(memoryViewPhoto)
            }
            media.contains("mp4") -> {
                val memoryViewVideo = VideoView(this)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.resources.getDimension(R.dimen.video_height).toInt())
                memoryViewVideo.layoutParams = params
                val controller = MediaController(this)
                memoryViewVideo.setMediaController(controller)

                memoryViewVideo.setVideoURI(Uri.parse(media))
                memoryViewVideo.requestFocus()
                memoryViewContents.addView(memoryViewVideo)
            }
        }
        memoryViewText.text = text
    }

    private fun showDialog() {
        loading_view.visibility = View.VISIBLE
    }

    fun hideDialog() {
        loading_view.visibility = View.INVISIBLE
    }
}
