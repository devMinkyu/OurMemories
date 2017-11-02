package com.kotlin.ourmemories.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.login.presenter.LoginContract
import com.kotlin.ourmemories.view.login.presenter.LoginPresenter

/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class LoginActivity : AppCompatActivity(){
    private lateinit var presenter:LoginContract.Presenter
    companion object {
        val START_DELAY = 300
        val ANIM_ITME_DURATION = 1000
        val ITEM_DELAY = 300
    }
    var animationStarted:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter().apply {
            activity = this@LoginActivity
        }

    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(!hasFocus or animationStarted){
            return
        }
        presenter.animation()
        super.onWindowFocusChanged(hasFocus)
    }

}



