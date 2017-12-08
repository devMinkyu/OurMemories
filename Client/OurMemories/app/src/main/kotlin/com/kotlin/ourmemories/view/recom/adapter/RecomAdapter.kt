package com.kotlin.ourmemories.view.recom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.data.jsondata.ReComMemoryResult
import com.kotlin.ourmemories.view.recom.RecomActivity

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
class RecomAdapter(context: Context, items:List<ReComMemoryResult>, activity:RecomActivity):RecyclerView.Adapter<RecomViewHolder>() {
    private var onItemClick: View.OnClickListener? = null
    private val mContext = context
    private val mItems = items
    private val mActivity = activity
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecomViewHolder = RecomViewHolder(parent)

    override fun onBindViewHolder(holder: RecomViewHolder?, position: Int) {
        val item = mItems.get(position)
        holder?.bindView(mContext, mActivity, item)
        holder?.itemView!!.setOnClickListener(onItemClick)
    }

    override fun getItemCount(): Int = mItems.size

    fun setOnItemClickListener(l:View.OnClickListener){
        onItemClick = l
    }
}