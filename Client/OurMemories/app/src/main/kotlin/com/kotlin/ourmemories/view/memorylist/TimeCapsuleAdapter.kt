package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.ourmemories.R
import com.kotlin.phonebook.adapter.CursorRecyclerViewAdapter
import java.util.*

/**
 * Created by nyoun_000 on 2017-11-11.
 */

class TimeCapsuleAdapter (context:Context, cursor:Cursor) : CursorRecyclerViewAdapter<TimeCapsuleAdapter.ViewHolder>(context,cursor){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null
    val mContext : Context = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_latitude : TextView = view.findViewById(R.id.tv_latitude) as TextView
        val tv_longitude : TextView = view.findViewById(R.id.tv_longitude) as TextView
        val tv_location : TextView = view.findViewById(R.id.tv_location) as TextView
        val tv_content : TextView = view.findViewById(R.id.tv_content) as TextView
        val iv_tag : ImageView = view.findViewById(R.id.tv_tag) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mainView : View = mInflater.inflate(R.layout.layout_memorylist_timecapsule_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ViewHolder(mainView)
    }
    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        val address = Geocoder(mContext, Locale.KOREAN).
                getFromLocation(cursor.getString(2).toDouble(), cursor.getString(3).toDouble(), 2)
        holder.tv_content.text = cursor.getString(1)
        holder.tv_location.text = address[0].getAddressLine(0).toString()
        holder.tv_latitude.text =  cursor.getString(2)
        holder.tv_longitude.text = cursor.getString(3)
//        holder.iv_tag.text = cursor.getString(0)
        if(cursor.getString(5).toInt() == 1){
            holder.iv_tag.setImageResource(R.drawable.review)
        } else {
            holder.iv_tag.setImageResource(R.drawable.timecapsule)
        }
    }

//    override fun getItemCount(): Int = cursor.columnCount

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }
}