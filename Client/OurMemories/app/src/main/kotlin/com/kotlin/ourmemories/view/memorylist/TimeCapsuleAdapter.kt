package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.R

/**
 * Created by nyoun_000 on 2017-11-11.
 */
data class TimeCapsuleData(
        var resId:Int,
        var name:String,
        var content:String,
        var _x:Double,
        var _y:Double
)
class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
    val tv_location : TextView = view.findViewById(R.id.tv_location) as TextView
    val tv_content : TextView = view.findViewById(R.id.tv_content) as TextView
    val tv_tag : TextView = view.findViewById(R.id.tv_tag) as TextView
}

class TimeCapsuleAdapter (val context:Context, val items:List<TimeCapsuleData>) : RecyclerView.Adapter<ViewHolder>(){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val mainView : View = mInflater.inflate(R.layout.layout_memorylist_timecapsule_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ViewHolder(mainView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_content.text = items[position].content
        holder.tv_location.text = "("+items[position]._x.toString() + ", " +items[position]._y+")"
        holder.tv_tag.text = position.toString()
    }
    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }
}