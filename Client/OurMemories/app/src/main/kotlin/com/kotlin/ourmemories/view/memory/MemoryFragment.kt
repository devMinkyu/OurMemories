package com.kotlin.ourmemories.view.memory

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var dBDateFormat:String
    companion object {

        val RIPPLE_DURATION: Long = 250
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_memory, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val memoryMenu = LayoutInflater.from(context).inflate(R.layout.memory_menu, null)
        memoryRoot.addView(memoryMenu)

        // memory_menu 애니메이션
        val menu = GuillotineAnimation.GuillotineBuilder(memoryMenu, memoryMenu.findViewById(R.id.memoryHamburger), contentHamburger)
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

        // memory 추가하기 위해 액티비티 변경 부분
        review.setOnClickListener {
            menu.close()
            presenter.intentActivity(ReviewActivity())
        }
        timeCapsule.setOnClickListener {
            menu.close()
            presenter.intentActivity(TimeCapsuleActivity())
        }

        // calendarView 부분
        calendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
        calendarView.addDecorators(SundayDecorator(), SaturdayDecorator(), OneDayDecorate())
        calendarView.setOnDateChangedListener(this)

        dayMemoryList.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        dBDateFormat = activity.resources.getString(R.string.date_format)
        val date = String.format(dBDateFormat, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        presenter.loadMemory(date)
    }

    // 유저가 선택한 날짜
    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        val date = String.format(dBDateFormat,date.year, date.month+1, date.day)
        presenter.loadMemory(date)
    }

    override fun onClick(v: View?) {}

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
