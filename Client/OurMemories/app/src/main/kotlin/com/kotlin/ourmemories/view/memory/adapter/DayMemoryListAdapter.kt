package com.kotlin.ourmemories.view.memory.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import com.kotlin.phonebook.adapter.CursorRecyclerViewAdapter

/**
 * Created by kimmingyu on 2017. 11. 21..
 */
class DayMemoryListAdapter(context: Context, cursor: Cursor): CursorRecyclerViewAdapter<DayMemoryListViewHolder>(context,cursor) {
    private var onItemClick: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DayMemoryListViewHolder = DayMemoryListViewHolder(parent)

    override fun onBindViewHolder(holder: DayMemoryListViewHolder, cursor: Cursor) {
        holder.itemView.setOnClickListener(onItemClick)
        // 추후 메모리 리스트와 합친후 진행
        //holder.bindView(cursor.getLong())
    }
    fun setOnItemClickListener(l:View.OnClickListener){
        onItemClick = l
    }
}