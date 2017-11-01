package com.kotlin.ourmemories.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import com.kotlin.ourmemories.MainActivity
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class LoginActivity : AppCompatActivity(){
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



    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(!hasFocus or animationStarted){
            return
        }
        animate()
        super.onWindowFocusChanged(hasFocus)
    }

    private fun animate(){
        ViewCompat.animate(img_logo).translationY((-250).toFloat()).setStartDelay((START_DELAY).toLong()).setDuration((ANIM_ITME_DURATION).toLong()).setInterpolator (
                DecelerateInterpolator(1.2f)).start()

        for(i in 0..container.childCount){
            var v: View? = container.getChildAt(i)
            var viewAnimator: ViewPropertyAnimatorCompat?

            if((v is ImageButton) or (v is Button)){
                viewAnimator = ViewCompat.animate(v).scaleY(1f).scaleX(1f).setStartDelay(((ITEM_DELAY *i)+500).toLong()).setDuration(500L)
            }else{
                viewAnimator = ViewCompat.animate(v).translationY(50f).alpha(1F).setStartDelay(((ITEM_DELAY *i)+500).toLong()).setDuration(1000L)
            }

            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }
    }
}



