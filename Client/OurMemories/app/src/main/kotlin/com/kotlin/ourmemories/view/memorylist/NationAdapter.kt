package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.R

/**
 * Created by nyoun_000 on 2017-11-13.
 */
data class NationData(
        var resId:Int,
        var name:String
)

class NationViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val tv_name : TextView = view.findViewById(R.id.tv_name) as TextView
    val tv_tag : TextView = view.findViewById(R.id.tv_tag) as TextView
}

class NationAdapter (val context:Context, val items:List<NationData>): RecyclerView.Adapter<NationViewHolder>(){
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var onItemClick:View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NationViewHolder? {
        val mainView : View = mInflater.inflate(R.layout.layout_nation_list_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return NationViewHolder(mainView)
    }
    override fun onBindViewHolder(holder: NationViewHolder, position: Int) {
        holder.tv_name.text = items[position].name
        holder.tv_tag.text = position.toString()
    }

    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l

    }


}