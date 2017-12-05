package com.kotlin.ourmemories.view.memory.presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.view.memory.MemoryFragment
import com.kotlin.ourmemories.view.memory.adapter.DayMemoryListAdapter
import kotlinx.android.synthetic.main.fragment_memory.*

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
    }


}