package com.kotlin.ourmemories.view.memorypin.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by kimmingyu on 2017. 11. 29..
 */
class MemoryPinAdapter(context:Context, items:List<Item>): RecyclerView.Adapter<MemoryPinViewHolder>() {
    private var onItemClick: View.OnClickListener? = null
    private val mContext = context
    private val mItems = items

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MemoryPinViewHolder = MemoryPinViewHolder(parent)

    override fun onBindViewHolder(holder: MemoryPinViewHolder?, position: Int) {
        val item = mItems.get(position)
        holder?.bindView(mContext, item)
        holder?.itemView!!.setOnClickListener(onItemClick)
    }

    override fun getItemCount(): Int = mItems.size

    fun setOnItemClickListener(l:View.OnClickListener){
        onItemClick = l
    }
}