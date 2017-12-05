package com.kotlin.ourmemories.view.login.presenter

import android.content.BroadcastReceiver
import android.content.Context
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.gson.Gson
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.jsondata.UserLogin
import com.kotlin.ourmemories.data.source.login.LoginRepository
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.service.fcm.QuickstartPreferences
import com.kotlin.ourmemories.service.fcm.RegistrationIntentService
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startService
import org.jetbrains.anko.yesButton
import java.io.IOException
import java.util.*

/**
 * Created by kimmingyu on 2017. 11. 2..
 */

class LoginPresenter: LoginContract.Presenter{
    lateinit override var activity: LoginActivity
    lateinit override var mLoginManager: LoginManager
    lateinit override var mRegistrationBroadcastReceiver: BroadcastReceiver
    lateinit override var callbackManager: CallbackManager
    lateinit override var loginData: LoginRepository

    lateinit var accessToken:String
    lateinit var registerId:String
    init {
        PManager.init()
    }
    // 네트워크 콜백받는 부분
    private val requestloginCallback:Callback = object :Callback{
        override fun onFailure(call: Call?, e: IOException?) {
            // 네트워크 에러
            activity.runOnUiThread {
                activity.hideDialog()
                activity.alert(activity.resources.getString(R.string.error_message_network), "Login"){
                    yesButton { mLoginManager.logOut() }
                }.show()
            }
        }

        override fun onResponse(call: Call?, response: Response?) {
            val responseData = response?.body()!!.string()
            val loginRequest:UserLogin = Gson().fromJson(responseData, UserLogin::class.java)

            val isSuccess = loginRequest.isSuccess

            when(isSuccess){
                "true/insert"->{
                    activity.runOnUiThread {
                        activity.hideDialog()
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

                        activity.startActivity<MainActivity>()
                        activity.finish()
                    }
                }
                "true/update"->{
                    activity.runOnUiThread {
                        activity.hideDialog()
                        //(이미 정보 존재 시 수정만 해준다.)공유저장소에 등록될 수정될 내용은 토큰값만 바꾸어 준다.
                        PManager.setUserFacebookId(accessToken)

                        activity.startActivity<MainActivity>()
                        activity.finish()
                    }
                }
                "false"->{
                    activity.runOnUiThread {
                        activity.hideDialog()
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

    override fun getInstanceIdToken() {
        if(checkPlayServices())
            activity.startService<RegistrationIntentService>()
    }

    override fun registBroadcastReceiver() {
        mRegistrationBroadcastReceiver = object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action

                when(action){
                    QuickstartPreferences.REGISTRATION_READY->{ } // 액션이 READY  경우
                    QuickstartPreferences.REGISTRATION_GENERATING->{} // 액션이 GENERATING 일 경우
                    QuickstartPreferences.REGISTRATION_COMPLETE->{
                        // 액션이 COMPLETE 일 경우
                        val token = intent.getStringExtra("token")
                        registerId = token

                        //토큰을 받은 이 후 로그인을 진행한다.//
                        //토큰을 받지 못하면 로그인 과정을 진행하지 않는다.//
                        facebookLogin()
                    }
                }
            }
        }
    }

    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    override fun checkPlayServices():Boolean {
        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, LoginActivity.PLAY_SERVICES_RESOLUTION_REQUEST).show()
            }else{
                activity.finish()
            }
            return false
        }
        return true
    }

    // 페이스북 로그인
    override fun facebookLogin() {
        mLoginManager.defaultAudience = DefaultAudience.FRIENDS
        mLoginManager.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK

        // 콜백 등록
        mLoginManager.registerCallback(callbackManager, object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                val facebookAccessToken:AccessToken = AccessToken.getCurrentAccessToken()

                accessToken = facebookAccessToken.token

                Log.d("LoginToken", "facebook token: ${accessToken}")

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

    // 로그인 되어있는지 검사
    override fun isLogin():Boolean{
        val token:AccessToken? = AccessToken.getCurrentAccessToken()
        return token != null
    }


    // 애니메이션
    override fun animation() {
        ViewCompat.animate(activity.img_logo).translationY((-250).toFloat()).setStartDelay((LoginActivity.START_DELAY).toLong()).setDuration((LoginActivity.ANIM_TIME_DURATION).toLong()).setInterpolator (
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