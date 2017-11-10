package com.kotlin.ourmemories.view.review

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        reviewTitleText.typeface = canaroExtraBold

        reviewBack.setOnClickListener {
            finish()
        }
    }
}
