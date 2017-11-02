package com.kotlin.ourmemories.view.login.presenter

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import com.kotlin.ourmemories.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by kimmingyu on 2017. 11. 2..
 */

class LoginPresenter: LoginContract.Presenter{
    lateinit override var activity: LoginActivity

    // 애니메이션
    override fun animation() {
        ViewCompat.animate(activity.img_logo).translationY((-250).toFloat()).setStartDelay((LoginActivity.START_DELAY).toLong()).setDuration((LoginActivity.ANIM_ITME_DURATION).toLong()).setInterpolator (
                DecelerateInterpolator(1.2f)).start()

        for(i in 0..activity.container.childCount){
            var v: View? = activity.container.getChildAt(i)
            var viewAnimator: ViewPropertyAnimatorCompat?

            if((v is ImageButton) or (v is Button)){
                viewAnimator = ViewCompat.animate(v).scaleY(1f).scaleX(1f).setStartDelay(((LoginActivity.ITEM_DELAY *i)+500).toLong()).setDuration(500L)
            }else{
                viewAnimator = ViewCompat.animate(v).translationY(50f).alpha(1F).setStartDelay(((LoginActivity.ITEM_DELAY *i)+500).toLong()).setDuration(1000L)
            }

            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }
    }

}