package com.kotlin.ourmemories.view.memorylist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.DB.DBManagerNation
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

//        DBManagerNation.init(context)
//        DBManagerNation.defaultAddNation()
//        var adapter = NationAdapter(context, DBManagerNation.getNationAllWithCursor())
//        var recycleListView = view.findViewById(R.id.nation_list) as RecyclerView
//        recycleListView.layoutManager = LinearLayoutManager(context)
//        recycleListView.adapter = adapter

        var recycleListView = view.findViewById(R.id.nation_list) as RecyclerView
        recycleListView.layoutManager = LinearLayoutManager(activity)

        DBManagerNation.init(activity)
//        DBManagerNation.defaultAddNation()

        var adapter = NationAdapter(activity, DBManagerNation.getNationAllWithCursor())
        recycleListView.adapter = adapter


    }

    override fun onStop() {
        super.onStop()
        DBManagerNation.close()
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}