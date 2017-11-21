package com.kotlin.ourmemories.view.memorylist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.fragment_memorylist.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class MemoryListFragment : Fragment() , View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorylist, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_to_memoryList.setOnClickListener {
            val intent = Intent(context, MemoryListActivity::class.java)
            startActivity(intent)
        }
        var adapter = NationAdapter(context, listOf(
                NationData(1,"대한민국"),
                NationData(2,"미국"),
                NationData(3,"영국"),
                NationData(4,"일본"),
                NationData(5,"러시아")
        ))
        var recycleListView = view.findViewById(R.id.nation_list) as RecyclerView

        recycleListView.layoutManager = LinearLayoutManager(context)
        recycleListView.adapter = adapter

    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}