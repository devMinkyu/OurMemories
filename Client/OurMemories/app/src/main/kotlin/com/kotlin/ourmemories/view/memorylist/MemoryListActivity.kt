package com.kotlin.ourmemories.view.memorylist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.activity_memory_list.*

class MemoryListActivity : AppCompatActivity(), View.OnClickListener {
    var nationName : String
    init {
        nationName = ""
        DBManagerMemory.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

//        //데이터베이스 오픈
//        DBManagerMemory.init(this)
//        DBManagerMemory.defaultAddTimeCapesule()
//        //getMemoryAllWithCursor를 통해 모든 데이터를 cursor에 담아서 어댑터에 담아줌.
//        var adapter = TimeCapsuleAdapter(this, DBManagerMemory.getMemoryAllWithCursor())
//        var recycleListView = findViewById(R.id.timecapsule_list) as RecyclerView
//        recycleListView.layoutManager = LinearLayoutManager(this)
//        recycleListView.adapter = adapter

        val intent : Intent = getIntent()
        nationName = intent.getStringExtra("nationName")



        //DBManagerMemory.defaultAddTimeCapesule()

        var adapter = TimeCapsuleAdapter(this, DBManagerMemory.getMemoriesWithCursor(nationName))
        timecapsule_list.layoutManager = LinearLayoutManager(this)
        timecapsule_list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryListMapFragment
        mapFragment.getMapAsync(mapFragment)


    }

    override fun onStop() {
        super.onStop()
        DBManagerMemory.close()
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
