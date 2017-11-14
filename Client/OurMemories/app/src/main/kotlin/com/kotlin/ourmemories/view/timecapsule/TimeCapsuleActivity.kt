package com.kotlin.ourmemories.view.timecapsule

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
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
            this.finish()
        }

        timeCapsuleFromTime.inputType = InputType.TYPE_NULL
        timeCapsuleToTime.inputType = InputType.TYPE_NULL
        timeCapsuleLocation.inputType = InputType.TYPE_NULL
        timeCapsuleAlarm.inputType = InputType.TYPE_NULL

        timeCapsuleDateText.setOnClickListener { presenter.dateTimeCapsule() }
        timeCapsuleFromTime.setOnClickListener {presenter.fromTimeTimeCapsule()}
        timeCapsuleToTime.setOnClickListener {presenter.toTimeTimeCapsule()}

    }
    // 날짜 선택 뷰
    override fun updateDateView(year:Int, monthOfYear:Int, dayOfMonth:Int) {
        val dataFormat = this.resources.getString(R.string.date_format)
        timeCapsuleDateText.text = String.format(dataFormat,year,monthOfYear,dayOfMonth)
    }
    // 시간 선택 뷰들
    override fun updateFromTimeView(hourOfDay:Int , minute:Int) {
        val timeFormat = this.resources.getString(R.string.time_format)
        timeCapsuleFromTime.setText(String.format(timeFormat, hourOfDay, minute))
    }
    override fun updateToTimeView(hourOfDay:Int , minute:Int) {
        val timeFormat = this.resources.getString(R.string.time_format)
        timeCapsuleToTime.setText(String.format(timeFormat, hourOfDay, minute))
    }
}
