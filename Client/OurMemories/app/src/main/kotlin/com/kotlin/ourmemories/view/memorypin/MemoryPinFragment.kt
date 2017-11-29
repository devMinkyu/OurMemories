package com.kotlin.ourmemories.view.memorypin

import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.memorypin.adapter.Item
import com.kotlin.ourmemories.view.memorypin.adapter.MemoryPinAdapter
import kotlinx.android.synthetic.main.fragment_memorypin.*


/**
 * Created by kimmingyu on 2017. 11. 12..
 */
class MemoryPinFragment: Fragment(),View.OnClickListener{
    var adapter: MemoryPinAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorypin, container, false)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        memoryPinRecyclerView.layoutManager = LinearLayoutManager(context)
        memoryPinRecyclerView.setHasFixedSize(true)

        // 배경 반투명화
        val alpha = memoryPin.background
        alpha.alpha = 200

        val items:ArrayList<Item> = ArrayList()
        val item = arrayOfNulls<Item>(2)
        item[0] = Item(R.color.timeCapsuleButton, "TimeCapsule", resources.getString(R.string.memory_pin_timecapsule_subtitle))
        item[1] = Item(R.color.reviewButton, "Review", resources.getString(R.string.memory_pin_review_subtitle))

        (0 until item.size).forEach { i ->
            items += item[i]!! }

        adapter = MemoryPinAdapter(context, items)
        memoryPinRecyclerView.adapter = adapter
        adapter?.setOnItemClickListener(this)
    }

    override fun onClick(p0: View?) {
    }
}
