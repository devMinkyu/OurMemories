package com.kotlin.ourmemories.view.timecapsule.presenter


import android.app.DatePickerDialog
import android.content.Context
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 14..
 */
class TimeCapsulePresenter(context: Context):TimeCapsuleContract.Presenter {
    lateinit override var mView: TimeCapsuleContract.View
    val mContext = context

    override fun dateTimeCapsule() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(mContext,listener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
    val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        mView.updateDateView(year,month+1,dayOfMonth)
    }
    override fun timeTimeCapsule() {
    }

    override fun currentAddress() {
    }

    override fun alarmTimeCapsule() {
    }

    override fun photoTimeCapsule() {

    }

    override fun videoTimeCapsule() {
    }

    override fun textTimeCapsule() {
    }

    override fun cameraCapsule() {
    }
}