package com.kotlin.ourmemories.view.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.data.source.profile.ProfileRepository
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
            profileData = ProfileRepository()
        }

        presenter.autoLogin()


    }
}