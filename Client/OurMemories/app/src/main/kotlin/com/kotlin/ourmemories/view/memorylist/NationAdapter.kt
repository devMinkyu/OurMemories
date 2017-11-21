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

/**
 * Created by nyoun_000 on 2017-11-13.
 */
//data class NationData(
//        var resId:Int,
//        var name:String
//)

//class NationViewHolder(view: View) : RecyclerView.ViewHolder(view){
//    val tv_name : TextView = view.findViewById(R.id.tv_name) as TextView
//    val tv_tag : TextView = view.findViewById(R.id.tv_tag) as TextView
//}

class NationAdapter (context: Context, cursor: Cursor): CursorRecyclerViewAdapter<NationAdapter.ViewHolder>(context, cursor){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tv_name : TextView = view.findViewById(R.id.tv_name) as TextView
        val tv_tag : TextView = view.findViewById(R.id.tv_tag) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mainView : View = mInflater.inflate(R.layout.layout_nation_list_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ViewHolder(mainView)
    }
    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        holder.tv_name.text = cursor.getString(1)
        holder.tv_tag.text = cursor.getString(0)
    }

    override fun getItemCount(): Int = cursor.columnCount

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }


}