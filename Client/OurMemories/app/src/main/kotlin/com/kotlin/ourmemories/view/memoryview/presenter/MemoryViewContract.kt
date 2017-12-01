package com.kotlin.ourmemories.view.memoryview.presenter

import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memoryview.MemoryViewActivity

/**
 * Created by kimmingyu on 2017. 12. 2..
 */
interface MemoryViewContract {
    interface Presenter{
        var activity:MemoryViewActivity
        var memoryData: MemoryRepository
    }
}