package com.kotlin.ourmemories.view.recom.presenter

import com.kotlin.ourmemories.data.jsondata.ReComMemoryResult
import com.kotlin.ourmemories.data.source.recom.RecomRepository
import com.kotlin.ourmemories.view.recom.RecomActivity
import com.kotlin.ourmemories.view.review.ReviewActivity

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
interface RecomContract {
    interface View{
        fun updateView(items:ArrayList<ReComMemoryResult>)
    }
    interface Presenter{
        var activity: RecomActivity
        var memoryData: RecomRepository
        var mView:View

        fun getReview(siDo:String, siGunGu:String)
    }
}