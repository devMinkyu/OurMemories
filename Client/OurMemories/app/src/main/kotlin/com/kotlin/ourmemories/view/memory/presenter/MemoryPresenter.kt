package com.kotlin.ourmemories.view.memory.presenter

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryLocalDataSource
import com.kotlin.ourmemories.view.memory.EventDecorator
import com.kotlin.ourmemories.view.memory.MemoryFragment
import com.kotlin.ourmemories.view.memory.adapter.DayMemoryListAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_memory.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by kimmingyu on 2017. 11. 10..
 */
class MemoryPresenter:MemoryContract.Presenter {
    lateinit override var mView: MemoryContract.View
    lateinit override var fragment: MemoryFragment

    override fun intentActivity(activity: AppCompatActivity) {
        fragment.startActivity(Intent(fragment.context, activity::class.java))
    }

    override fun loadMemory(date: String) {
        DBManagerMemory.init(fragment.context)
        val memoryOne = DBManagerMemory.getMemoryDayWithCursor(date)
        if(memoryOne.count !=0) {
            fragment.adapter = DayMemoryListAdapter(fragment.context, memoryOne)
            fragment.dayMemoryList.adapter = fragment.adapter
            fragment.adapter?.setOnItemClickListener(fragment)
        }
        if(fragment.adapter != null){
            fragment.adapter?.changeCursor(memoryOne)
            fragment.adapter?.notifyDataSetChanged()
        }
        DBManagerMemory.close()
    }

    override fun existMemory() {
        DBManagerMemory.init(fragment.context)
        val calendar:Calendar = Calendar.getInstance()
        val cursor = DBManagerMemory.getMemoryAllWithCursor()
        val dates:ArrayList<CalendarDay> = ArrayList()
        cursor.moveToFirst()
        cursor?.let {
            calendar.set(Calendar.MONTH, extractMonth(cursor.getString(5)))
            calendar.set(Calendar.DAY_OF_MONTH, extractDay(cursor.getString(5)))
            val day:CalendarDay = CalendarDay.from(calendar)
            fragment.calendarView.addDecorators(EventDecorator(Color.RED, day))
            //dates.add(day)
        }
//        dates?.let {
//            fragment.calendarView.addDecorators(EventDecorator(Color.RED, it))
//        }
        DBManagerMemory.close()
    }

    fun extractMonth(date:String):Int{
        val yearFormat = fragment.resources.getString(R.string.year_format)
        val monthFormat = fragment.resources.getString(R.string.month_format)
        val index = date.indexOf(yearFormat)
        val index2 = date.indexOf(monthFormat)
        return date.substring(index+2, index2).toInt() - 1
    }
    fun extractDay(date:String):Int{
        val monthFormat = fragment.resources.getString(R.string.month_format)
        val dayFormat = fragment.resources.getString(R.string.day_format)
        val index = date.indexOf(monthFormat)
        val index2 = date.indexOf(dayFormat)
        return date.substring(index+2, index2).toInt()
    }
}