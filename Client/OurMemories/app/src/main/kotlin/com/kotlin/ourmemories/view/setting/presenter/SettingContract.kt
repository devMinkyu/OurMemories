package com.kotlin.ourmemories.view.setting.presenter

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by kimmingyu on 2017. 12. 4..
 */
interface SettingContract {
    interface Presenter{
        var fragment:Fragment
        var mContext:Context

        fun logOut()
        fun profile()
    }
}