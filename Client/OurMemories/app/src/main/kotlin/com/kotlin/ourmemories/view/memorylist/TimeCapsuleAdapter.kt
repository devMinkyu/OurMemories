package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.phonebook.adapter.CursorRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_memorylist_timecapsule_item.view.*
import java.util.*

/**
 * Created by nyoun_000 on 2017-11-11.
 */

class TimeCapsuleAdapter (context:Context, cursor:Cursor) : CursorRecyclerViewAdapter<TimeCapsuleAdapter.ViewHolder>(context,cursor){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null
    val mContext : Context = context

    class ViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        val mView= view
        val mContext = context
        fun bindView(cursor: Cursor) {
            val address = Geocoder(this.mContext, Locale.KOREAN).
                    getFromLocation(cursor.getString(2).toDouble(), cursor.getString(3).toDouble(), 2)
            with(mView){
                tv_content.text = cursor.getString(1)
                tv_location.text = address[0].getAddressLine(0).toString()
                tv_latitude.text = cursor.getString(2)
                tv_longitude.text = cursor.getString(3)
                when(cursor.getString(5).toInt()){
                    0-> {tv_tag.setImageResource(R.drawable.timecapsule)}
                    1->{tv_tag.setImageResource(R.drawable.review)}
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mainView : View = mInflater.inflate(R.layout.layout_memorylist_timecapsule_item, parent, false)
        return ViewHolder(mainView, mContext)
    }
    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        holder.itemView.setOnClickListener(onItemClick)
        holder.bindView(cursor)
    }


    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }
}