package com.kotlin.ourmemories.view.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.view.login.LoginActivity

/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class SplashActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}