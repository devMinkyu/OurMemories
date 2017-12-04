package com.kotlin.ourmemories.view.profile

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.MyApplication.Companion.context
import com.kotlin.ourmemories.view.MainActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val canaroExtraBold = Typeface.createFromAsset(context.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        profileTitleText.typeface = canaroExtraBold

        profileBack.setOnClickListener {
            finish()
        }
    }
}
