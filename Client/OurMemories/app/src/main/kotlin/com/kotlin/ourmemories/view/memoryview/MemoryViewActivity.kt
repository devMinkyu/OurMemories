package com.kotlin.ourmemories.view.memoryview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memoryview.presenter.MemoryViewContract
import com.kotlin.ourmemories.view.memoryview.presenter.MemoryViewPresenter

class MemoryViewActivity : AppCompatActivity() {
    private lateinit var presenter:MemoryViewContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_view)

        val intent = intent
        val id = intent.getStringExtra("id")

        presenter = MemoryViewPresenter().apply {
            memoryData = MemoryRepository(this@MemoryViewActivity)
            activity = this@MemoryViewActivity
        }
    }
}
