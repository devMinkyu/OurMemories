package com.kotlin.ourmemories.view.memorylist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.R

class MemoryListActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

        //맵관련
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryMapFragment
//        mapFragment.getMapAsync(mapFragment)

        //리사이클리스트 관련
//        var adapter = TimeCapsuleAdapter(this, listOf(
//                TimeCapsuleData(1,"타임1", 33.33,22.22, "나라1", 0),
//                TimeCapsuleData(2,"타임2",  10.3,20.11, "나라2", 0)
//        ))
        //데이터베이스 오픈
        DBManagerMemory.init(this)
        DBManagerMemory.defaultAddTimeCapesule()

//        //getMemoryAllWithCursor를 통해 모든 데이터를 cursor에 담아서 어댑터에 담아줌.
        var adapter = TimeCapsuleAdapter(this, DBManagerMemory.getMemoryAllWithCursor())



        var recycleListView = findViewById(R.id.timecapsule_list) as RecyclerView
        recycleListView.layoutManager = LinearLayoutManager(this)
        recycleListView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryMapFragment
        mapFragment.getMapAsync(mapFragment)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
