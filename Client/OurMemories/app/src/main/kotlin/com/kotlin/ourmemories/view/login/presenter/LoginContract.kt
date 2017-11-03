package com.kotlin.ourmemories.view.login.presenter

import com.kotlin.ourmemories.view.login.LoginActivity

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
interface LoginContract {
    interface Presenter{
        var activity:LoginActivity
        fun animation()
    }
}