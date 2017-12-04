package com.kotlin.ourmemories.view.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.data.source.autologin.AutoLoginRepository
import com.kotlin.ourmemories.view.splash.presenter.SplashContract
import com.kotlin.ourmemories.view.splash.presenter.SplashPresenter

/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class SplashActivity :AppCompatActivity(){

    private lateinit var presenter:SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SplashPresenter().apply {
            activity = this@SplashActivity
            profileData = AutoLoginRepository()
        }

        presenter.autoLogin()


    }
}