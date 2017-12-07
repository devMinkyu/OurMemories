package com.kotlin.ourmemories.view.place.presenter

import android.content.Context
import android.support.v4.app.Fragment
import com.kotlin.ourmemories.view.place.adapter.data.SiDo

/**
 * Created by hee on 2017-11-07.
 */
interface PlaceContract {

    interface View{
        fun updateCityView(siDo: List<SiDo>)
    }
    interface Presenter{
        var fragment:Fragment
        var mView:View
        var mContext:Context

        fun getCityDate()
    }
}