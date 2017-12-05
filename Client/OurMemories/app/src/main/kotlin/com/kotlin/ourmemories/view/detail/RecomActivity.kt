package com.kotlin.ourmemories.view.detail

/**
 * Created by hee on 2017-11-28.
 */
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.activity_recom.*
import java.io.InputStream
import java.io.InputStreamReader

class RecomActivity : AppCompatActivity() {
    companion object {
        val EXTRA_SIGUNGU_NAME = "sigungu_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sigungu = intent.getStringExtra(EXTRA_SIGUNGU_NAME)
        setContentView(R.layout.activity_recom)
    }
}
