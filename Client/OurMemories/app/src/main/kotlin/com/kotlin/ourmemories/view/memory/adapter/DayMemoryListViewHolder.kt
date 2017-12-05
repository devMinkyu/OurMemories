package com.kotlin.ourmemories.view.memory.adapter

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.day_memory_list.view.*
import org.jetbrains.anko.image

/**
 * Created by kimmingyu on 2017. 11. 21..
 */

class DayMemoryListViewHolder(parent: ViewGroup?):RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.day_memory_list,parent,false)) {
    fun bindView(cursor: Cursor){
        with(itemView){
            title.text = cursor.getString(1)
            date.text = cursor.getString(5)
            if(cursor.getString(7) == "1"){
                memory.image = resources.getDrawable(R.drawable.review)
            }else{
                memory.image = resources.getDrawable(R.drawable.timecapsule)
            }
            title.tag = cursor.getString(0)
        }
    }
}