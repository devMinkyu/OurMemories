package com.kotlin.ourmemories.view.place.adapter.viewholder

/**
 * Created by hee on 2017-11-28.
 */
import android.view.View
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.list_item_sigungu.view.*


class SiGunGuViewHolder(itemView: View) : ChildViewHolder(itemView) {
    fun setSiGunGuName(name: String) {
        itemView.list_item_sigungu_name.text = name
    }
}