package com.kotlin.ourmemories.view.timecapsule

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import kotlinx.android.synthetic.main.activity_timecapsule.*

class TimeCapsuleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecapsule)

        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        timeCapsuleTitleText.typeface = canaroExtraBold

        timeCapsuleBack.setOnClickListener {
            finish()
        }
    }
}
