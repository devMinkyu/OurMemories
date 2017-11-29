package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.R
import com.kotlin.phonebook.adapter.CursorRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_memorylist_timecapsule_item.view.*

/**
 * Created by nyoun_000 on 2017-11-11.
 */
//data class TimeCapsuleData(
//        var id:Int,
//        var title:String,
//        var latitude:Double,
//        var longitude:Double,
//        var nationName:String,
//        var classification:Int
//)


class TimeCapsuleAdapter (context:Context, cursor:Cursor) : CursorRecyclerViewAdapter<TimeCapsuleAdapter.ViewHolder>(context,cursor){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mView= view
        fun bindView(cursor: Cursor){
            with(mView){
                tv_content.text = cursor.getString(1)
                tv_location.text = "("+cursor.getString(2) + ", " +cursor.getString(3)+")"
                tv_tag.text = cursor.toString()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mainView : View = mInflater.inflate(R.layout.layout_memorylist_timecapsule_item, parent, false)
        return ViewHolder(mainView)
    }
    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        holder.itemView.setOnClickListener(onItemClick)
        holder.bindView(cursor)
    }

    override fun getItemCount(): Int = cursor.columnCount

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }
}