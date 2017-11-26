package com.kotlin.ourmemories.view.memory

import android.database.Cursor
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.MemoryData
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity.Companion.CANARO_EXTRA_BOLD_PATH
import com.kotlin.ourmemories.view.memory.adapter.DayMemoryListAdapter
import com.kotlin.ourmemories.view.memory.presenter.MemoryContract
import com.kotlin.ourmemories.view.memory.presenter.MemoryPresenter
import com.kotlin.ourmemories.view.review.ReviewActivity
import com.kotlin.ourmemories.view.timecapsule.TimeCapsuleActivity
import com.prolificinteractive.materialcalendarview.*
import com.yalantis.guillotine.animation.GuillotineAnimation
import kotlinx.android.synthetic.main.fragment_memory.*
import kotlinx.android.synthetic.main.memory_menu.*
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class MemoryFragment : Fragment(), MemoryContract.View, OnDateSelectedListener, View.OnClickListener {
    private lateinit var presenter: MemoryContract.Presenter

    var adapter: DayMemoryListAdapter? = null

    companion object {

        val RIPPLE_DURATION: Long = 250
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_memory, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        DBManagerMemory.init(context)
        val memoryMenu = LayoutInflater.from(context).inflate(R.layout.memory_menu, null)
        memoryRoot.addView(memoryMenu)

        // 우선 adater에 recyclerview를 붙이기 위한 adapter를 할당해주고 그럼 다음 리스트가 있는 xml의 adapter에 붙여준다
        dayMemoryList.layoutManager = LinearLayoutManager(context)

        // memory_menu 애니메이션
        GuillotineAnimation.GuillotineBuilder(memoryMenu, memoryMenu.findViewById(R.id.memoryHamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build()

        // memory, memory menu 글씨체 변경
        val canaroExtraBold = Typeface.createFromAsset(context.assets, CANARO_EXTRA_BOLD_PATH)
        titleText.typeface = canaroExtraBold
        with(memoryMenu) {
            memoryTitleText.typeface = canaroExtraBold
            review.typeface = canaroExtraBold
            timeCapsule.typeface = canaroExtraBold
        }

        // presenter 연결부분
        presenter = MemoryPresenter().apply {
            fragment = this@MemoryFragment
            mView = this@MemoryFragment
        }

        review.setOnClickListener {
            presenter.intentActivity(ReviewActivity())
        }
        timeCapsule.setOnClickListener {
            presenter.intentActivity(TimeCapsuleActivity())
        }

        calendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
        calendarView.addDecorators(SundayDecorator(), SaturdayDecorator(), OneDayDecorate())

        calendarView.setOnDateChangedListener(this)

        dayMemoryList.layoutManager = LinearLayoutManager(context)
        var memoryOne = DBManagerMemory.getMemoryAllWithCursor()
        memoryOne.moveToFirst()

//        var dayOne:Cursor
//        Log.d("hoho", memoryOne.count.toString())
//        for(memoryData in 0 until memoryOne.count){
//            Log.d("hoho", memoryOne.getString(5))
//            dayOne.extras = memoryOne
//            memoryOne.moveToNext()
//        }

        if(memoryOne.count !=0) {
            adapter = DayMemoryListAdapter(context, memoryOne)
            dayMemoryList.adapter = adapter
            adapter?.setOnItemClickListener(this)
        }
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        Log.d("hoho", date.month.toString())
        var memoryOne = DBManagerMemory.getMemoryAllWithCursor()
        memoryOne.moveToFirst()
        if(adapter == null){
            adapter = DayMemoryListAdapter(context, memoryOne)
            dayMemoryList.adapter = adapter
            adapter?.setOnItemClickListener(this)
        }
        adapter?.changeCursor(memoryOne)
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// 오늘 날짜 표시
class OneDayDecorate : DayViewDecorator {
    private val date = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean = (date != null) and (day!! == date)

    override fun decorate(view: DayViewFacade) {
        with(view) {
            addSpan(StyleSpan(Typeface.BOLD))
            addSpan(RelativeSizeSpan(1.4f))
        }
    }

}

// 일요일 표시
class SaturdayDecorator : DayViewDecorator {
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.BLUE))
    }

}

// 토요일 표시
class SundayDecorator : DayViewDecorator {
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.RED))
    }

}
