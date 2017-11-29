package com.kotlin.ourmemories.view.memorylist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.DB.DBManagerNation
import com.kotlin.ourmemories.R

/**
 * Created by kimmingyu on 2017. 11. 5..
 */

class MemoryListFragment : Fragment() , View.OnClickListener {
//    fun init(context : Context = activity){
//        DBManagerMemory.init(context)
//        DBManagerMemory.defaultAddTimeCapesule()
//    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorylist, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //프래그먼트 열릴때 디비 생성
        DBManagerMemory.init(activity)

        //nation_list 리사이클 뷰를 Fragment에 셋팅
        var recycleListView = view.findViewById(R.id.nation_list) as RecyclerView
        recycleListView.layoutManager = LinearLayoutManager(activity)

        //데이터베이스를 리사이클 뷰에 뿌려줌.
        var adapter = NationAdapter(activity, DBManagerMemory.getMemoryAllNationWithCursor())
        adapter.setOnItemClickListener(this)
        recycleListView.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        DBManagerNation.close()
    }

    override fun onClick(v: View?) {
        val tv : TextView = v?.findViewById(R.id.tv_tag) as TextView
        val tvName : TextView = v?.findViewById(R.id.tv_name) as TextView
        //context.toast(tv.text.toString() + ", " + tvName.text.toString() + " Clicked")
        val intent = Intent(context, MemoryListActivity::class.java)
        intent.putExtra("nationName", tvName.text.toString())
        startActivity(intent)
    }
}