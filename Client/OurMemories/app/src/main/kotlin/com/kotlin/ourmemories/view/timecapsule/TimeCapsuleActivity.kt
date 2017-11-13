package com.kotlin.ourmemories.view.timecapsule

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsuleContract
import com.kotlin.ourmemories.view.timecapsule.presenter.TimeCapsulePresenter
import kotlinx.android.synthetic.main.activity_timecapsule.*

class TimeCapsuleActivity : AppCompatActivity(), TimeCapsuleContract.View {
    private lateinit var presenter:TimeCapsuleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecapsule)

        presenter = TimeCapsulePresenter(this).apply {
            mView = this@TimeCapsuleActivity
        }

        // 폰트 변경
        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        timeCapsuleTitleText.typeface = canaroExtraBold

        //뒤로가기
        timeCapsuleBack.setOnClickListener {
            finish()
        }

        timeCapsuleDateText.setOnClickListener { presenter.dateTimeCapsule() }

    }

    override fun updateDateView(year:Int, monthOfYear:Int, dayOfMonth:Int) {
        var dataFormat = this.resources.getString(R.string.date_format);
        timeCapsuleDateText.text = String.format(dataFormat,year,monthOfYear,dayOfMonth)
    }

    override fun updateTimeView() {
    }
}
