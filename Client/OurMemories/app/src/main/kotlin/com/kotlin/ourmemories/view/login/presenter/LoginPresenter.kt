package com.kotlin.ourmemories.view.login.presenter

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.DefaultAudience
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import com.kotlin.ourmemories.data.jsondata.UserLogin
import com.kotlin.ourmemories.data.source.login.LoginRepository
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 2..
 */

class LoginPresenter: LoginContract.Presenter{
    lateinit override var activity: LoginActivity

    lateinit override var mLoginManager: LoginManager
    lateinit override var callbackManager: CallbackManager
    lateinit override var loginData: LoginRepository
    lateinit var accessToken:String
    init {
        PManager.init()
    }

    private var requestloginCallback:Callback = object :Callback{
        override fun onFailure(call: Call?, e: IOException?) {
            // 네트워크 에러
            Log.d("hoho", "error message: ${e?.message}")

            activity.runOnUiThread {
                activity.hidepDialog()

                val alertDialog = AlertDialog.Builder(activity)
                alertDialog.setTitle("Login")
                        .setMessage("요청에러 (네트워크 상태를 점검해주세요)")
                        .setCancelable(false)
                        .setPositiveButton("확인") { dialog, which -> mLoginManager.logOut()}
                val alert = alertDialog.create()
                alert.show()
            }
        }

        override fun onResponse(call: Call?, response: Response?) {
            val responseData = response?.body()!!.string()
            val loginRequest:UserLogin = Gson().fromJson(responseData, UserLogin::class.java)

            val isSuccess = loginRequest.IsSuccess

            activity.hidepDialog()
            when(isSuccess){
                "true/insert"->{
                    activity.runOnUiThread {
                        // 공유저장소에 저장전 한번 초기화 시켜준다
                        PManager.setUserId("")
                        PManager.setUserEmail("")
                        PManager.setUserName("")
                        PManager.setUserFacebookId("")
                        PManager.setUserProfileImageUrl("")

                        // 서버로 부터 온 데이터 공유저장소에 저장
                        PManager.setUserId(loginRequest.userLoginResult.userId)
                        PManager.setUserEmail(loginRequest.userLoginResult.userEmail)
                        PManager.setUserName(loginRequest.userLoginResult.userName)
                        PManager.setUserFacebookId(accessToken)
                        PManager.setUserProfileImageUrl(loginRequest.userLoginResult.userProfileImageUrl)

                        activity.startActivity(Intent(activity,MainActivity::class.java))
                        activity.finish()
                    }
                }
                "true/update"->{
                    activity.runOnUiThread {
                        //(이미 정보 존재 시 수정만 해준다.)공유저장소에 등록될 수정될 내용은 토큰값만 바꾸어 준다.
                        PManager.setUserFacebookId(accessToken)

                        activity.startActivity(Intent(activity,MainActivity::class.java))
                        activity.finish()
                    }
                }
                "false"->{
                    activity.runOnUiThread {

                        val alertDialog = AlertDialog.Builder(activity)
                        alertDialog.setTitle("Login")
                                .setMessage("로그인 실패 (다시 접속해주세)")
                                .setCancelable(false)
                                .setPositiveButton("확인") { dialog, which -> mLoginManager.logOut()}
                        val alert = alertDialog.create()
                        alert.show()
                    }
                }
            }
        }

    }

    override fun facebookLogin() {
        mLoginManager.defaultAudience = DefaultAudience.FRIENDS
        mLoginManager.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK

        // 콜백 등록
        mLoginManager.registerCallback(callbackManager, object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                val facebookAccessToken:AccessToken = AccessToken.getCurrentAccessToken()

                accessToken = facebookAccessToken.token

                Log.d("hoho", "token: ${accessToken}")

                loginData.loginServer(accessToken,requestloginCallback,activity)

                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
            }
        })

        mLoginManager.logInWithReadPermissions(activity, Arrays.asList("email"))
    }

    override fun isLogin():Boolean{
        val token:AccessToken? = AccessToken.getCurrentAccessToken()


        return token != null
    }


    // 애니메이션
    override fun animation() {
        ViewCompat.animate(activity.img_logo).translationY((-250).toFloat()).setStartDelay((LoginActivity.START_DELAY).toLong()).setDuration((LoginActivity.ANIM_ITME_DURATION).toLong()).setInterpolator (
                DecelerateInterpolator(1.2f)).start()

        for(i in 0..activity.container.childCount){
            val v: View? = activity.container.getChildAt(i)
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