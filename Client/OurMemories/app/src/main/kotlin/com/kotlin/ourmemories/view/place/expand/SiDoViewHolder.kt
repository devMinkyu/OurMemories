package com.kotlin.ourmemories.view.place.expand

/**
 * Created by hee on 2017-11-28.
 */
import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import com.kotlin.ourmemories.view.place.SiDo
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.list_item_sido.view.*

class SiDoViewHolder(itemView: View) : GroupViewHolder(itemView) {
    fun setGenreTitle(genre: ExpandableGroup<*>) {
        if (genre is SiDo) {
            itemView.list_item_sido_name.text = genre.getTitle()
        }
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360f, 180f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        itemView.list_item_arrow.animation = rotate
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180f, 360f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        itemView.list_item_arrow.animation = rotate
    }

}