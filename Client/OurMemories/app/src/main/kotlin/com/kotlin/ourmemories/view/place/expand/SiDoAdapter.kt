package com.kotlin.ourmemories.view.place.expand

/**
 * Created by hee on 2017-11-28.
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.place.SiDo
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class SiDoAdapter(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<SiDoViewHolder, SiGunGuViewHolder>(groups) {

    private var onItemClick:View.OnClickListener? = null
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): SiDoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_sido, parent, false)
        return SiDoViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): SiGunGuViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_sigungu, parent, false)
        return SiGunGuViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: SiGunGuViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>, childIndex: Int) {

        val siGunGu = (group as SiDo).items[childIndex]
        holder.setSiGunGuName(siGunGu.name!!)
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun onBindGroupViewHolder(holder: SiDoViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>) {

        holder.setGenreTitle(group)
    }
    fun setOnclickListener(l:View.OnClickListener){
        onItemClick = l
    }

}