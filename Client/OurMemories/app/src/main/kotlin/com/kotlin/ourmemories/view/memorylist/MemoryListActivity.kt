package com.kotlin.ourmemories.view.memorylist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.R

class MemoryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryMapFragment
        mapFragment.getMapAsync(mapFragment)
    }
}
