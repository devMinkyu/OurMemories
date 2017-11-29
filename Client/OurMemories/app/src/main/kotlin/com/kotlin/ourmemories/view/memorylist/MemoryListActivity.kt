package com.kotlin.ourmemories.view.memorylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.R

class MemoryListActivity : AppCompatActivity(), View.OnClickListener {
    fun init(context: Context = this){
        DBManagerMemory.init(context)
    }
    var nationName : String
    init {
        nationName = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

        //Fragment에서 넘겨받은 intent 받는 로직
        val intent : Intent = getIntent()
        nationName = intent.getStringExtra("nationName")


        //엑티비티에 리사이클 뷰 달아주는 로직
        var recycleListView = findViewById(R.id.timecapsule_list) as RecyclerView
        recycleListView.layoutManager = LinearLayoutManager(this)


        //DBManagerMemory.init(this)
        //DBManagerMemory.defaultAddTimeCapesule()

        //리사이클 뷰에 DB내용 넣어주는 로직
        var adapter = TimeCapsuleAdapter(this, DBManagerMemory.getMemoriesWithCursor(nationName))
        recycleListView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        //fragment자리에 map fragment달아주는 작업
        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryListMapFragment
        mapFragment.getMapAsync(mapFragment)


    }
    override fun onStop() {
        super.onStop()

        DBManagerMemory.close()
    }

    //리사이클뷰 아이템 클릭 이벤트
    fun onClickTimeCapsual(v: View?){
        val tv_latitude = v?.findViewById(R.id.tv_latitude) as TextView
        val tv_longitude = v?.findViewById(R.id.tv_longitude) as TextView
        //지도 카메라를 해당 아이템 좌표로 옮김.
        var mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryListMapFragment
        mapFragment.moveToMapCameraPosition(LatLng(tv_latitude.text.toString().toDouble(), tv_longitude.text.toString().toDouble()),
                15.0f)
    }

    override fun onClick(v: View?) {
    }
}
