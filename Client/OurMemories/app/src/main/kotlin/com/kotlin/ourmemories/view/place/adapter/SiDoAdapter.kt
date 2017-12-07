package com.kotlin.ourmemories.view.place.adapter

/**
 * Created by hee on 2017-11-28.
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.place.adapter.data.SiDo
import com.kotlin.ourmemories.view.place.adapter.viewholder.SiDoViewHolder
import com.kotlin.ourmemories.view.place.adapter.viewholder.SiGunGuViewHolder
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class SiDoAdapter(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<SiDoViewHolder, SiGunGuViewHolder>(groups) {

    private var onItemClick:View.OnClickListener? = null
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): SiDoViewHolder = SiDoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_sido, parent, false))
    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): SiGunGuViewHolder = SiGunGuViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_sigungu, parent, false))


    override fun onBindGroupViewHolder(holder: SiDoViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }

    override fun onBindChildViewHolder(holder: SiGunGuViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
        val siGunGu = (group as SiDo).items[childIndex]
        holder.setSiGunGuName(siGunGu.name!!)
        holder.itemView.setOnClickListener(onItemClick)
    }
    fun setOnclickListener(l:View.OnClickListener){
        onItemClick = l
    }

}