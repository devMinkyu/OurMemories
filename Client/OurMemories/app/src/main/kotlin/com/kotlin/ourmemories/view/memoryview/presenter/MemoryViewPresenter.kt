package com.kotlin.ourmemories.view.memoryview.presenter

import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memoryview.MemoryViewActivity

/**
 * Created by kimmingyu on 2017. 12. 2..
 */
class MemoryViewPresenter:MemoryViewContract.Presenter {
    lateinit override var activity: MemoryViewActivity
    lateinit override var memoryData: MemoryRepository
}