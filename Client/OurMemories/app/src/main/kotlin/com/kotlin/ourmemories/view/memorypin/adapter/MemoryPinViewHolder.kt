package com.kotlin.ourmemories.view.memorypin.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity.Companion.CANARO_EXTRA_BOLD_PATH
import kotlinx.android.synthetic.main.cardview_button.view.*
import org.jetbrains.anko.backgroundColor

/**
 * Created by kimmingyu on 2017. 11. 29..
 */
class MemoryPinViewHolder(parent: ViewGroup?):RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.cardview_button,parent,false)) {
    fun bindView(context:Context, item:Item){
        val color = ContextCompat.getColor(context,item.color)
        // memory, memory menu 글씨체 변경
        val canaroExtraBold = Typeface.createFromAsset(context.assets, CANARO_EXTRA_BOLD_PATH)
        with(itemView){
            memoryPinBackground.backgroundColor = color
            memoryPinTitle.text = item.title
            memoryPinTitle.typeface = canaroExtraBold
            memoryPinSubTitle.text = item.subTitle
            memoryPinTitle.tag = item.id
        }
    }
}