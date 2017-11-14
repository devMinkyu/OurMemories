package com.kotlin.ourmemories.view.memorylist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kotlin.ourmemories.R

class MemoryListActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

        //맵관련
        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryMapFragment
        mapFragment.getMapAsync(mapFragment)

        //리사이클리스트 관련
        var adapter = TimeCapsuleAdapter(this, listOf(
                TimeCapsuleData(1,"위치1", "냉무1", 33.33,22.22),
                TimeCapsuleData(2,"위치2", "냉무2", 10.3,20.11),
                TimeCapsuleData(3,"위치3", "냉무3", 50.2,32.11),
                TimeCapsuleData(4,"위치4", "냉무4", 20.3,21.11),
                TimeCapsuleData(5,"위치5", "냉무5", 30.3,26.11),
                TimeCapsuleData(6,"위치6", "냉무6", 40.3,24.11),
                TimeCapsuleData(7,"위치7", "냉무7", 50.3,23.11),
                TimeCapsuleData(8,"위치8", "냉무8", 60.3,23.11),
                TimeCapsuleData(9,"위치9", "냉무9", 38.3,44.11),
                TimeCapsuleData(10,"위치10", "냉무10", 23.3,31.11),
                TimeCapsuleData(11,"위치11", "냉무11", 42.3,77.11),
                TimeCapsuleData(12,"위치12", "냉무12", 62.3,63.11),
                TimeCapsuleData(13,"위치13", "냉무13", 44.3,52.11),
                TimeCapsuleData(14,"위치14", "냉무14", 15.3,9.11)

        ))

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
