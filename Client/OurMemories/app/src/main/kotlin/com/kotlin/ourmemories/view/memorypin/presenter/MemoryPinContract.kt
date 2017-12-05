package com.kotlin.ourmemories.view.memorypin.presenter

import com.google.android.gms.common.api.GoogleApiClient
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memorypin.MemoryPinFragment

/**
 * Created by kimmingyu on 2017. 11. 12..
 */
interface MemoryPinContract {
    interface View
    interface Presenter{
        var mView: View
        var fragment: MemoryPinFragment
        var mGoogleApiClient: GoogleApiClient?
        var memoryData: MemoryRepository

        fun isMemoryCheck(classification:Int)
    }
}