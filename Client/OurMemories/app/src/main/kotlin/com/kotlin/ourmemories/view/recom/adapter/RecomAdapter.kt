package com.kotlin.ourmemories.view.recom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.data.jsondata.ReComMemoryResult

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
class RecomAdapter(context: Context, items:List<ReComMemoryResult>):RecyclerView.Adapter<RecomViewHolder>() {
    private val mContext = context
    private val mItems = items
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecomViewHolder = RecomViewHolder(parent)

    override fun onBindViewHolder(holder: RecomViewHolder?, position: Int) {
        val item = mItems.get(position)
        holder?.bindView(mContext, item)
    }

    override fun getItemCount(): Int = mItems.size
}