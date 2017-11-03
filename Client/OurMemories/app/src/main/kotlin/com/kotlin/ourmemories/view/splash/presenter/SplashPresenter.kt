package com.kotlin.ourmemories.view.splash.presenter

import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AlertDialog
import com.google.gson.Gson
import com.kotlin.ourmemories.MainActivity
import com.kotlin.ourmemories.data.source.profile.ProfileRepository
import com.kotlin.ourmemories.data.source.profile.UserProfile
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.view.login.LoginActivity
import com.kotlin.ourmemories.view.splash.SplashActivity
import okhttp3.*
import java.io.IOException

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
class SplashPresenter: SplashContract.Presenter {
    lateinit override var profileData: ProfileRepository
    lateinit override var activity:SplashActivity

    override val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
    val userId:String by lazy {
        PManager.getUserId()
    }

    init {
        PManager.init()
    }

    // 서버 전송시 callBack 받는 부분
    private val requestProfileCallback:Callback = object :Callback{
        // 실패 했을 경우
        override fun onFailure(call: Call?, e: IOException?) {
            if(activity != null){
                activity.runOnUiThread(Runnable {
                    val alertDialog = AlertDialog.Builder(activity)
                    alertDialog.setTitle("Login")
                            .setMessage("요청에러 (네트워크 상태를 점검해주세요)")
                            .setCancelable(false)
                            .setPositiveButton("확인", object: DialogInterface.OnClickListener{
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    activity.finish()
                                }
                            })
                    val alert = alertDialog.create()
                    alert.show()
                })
            }
        }
        // 성공 했을 경우
        override fun onResponse(call: Call?, response: Response?) {
            val responseData:String = response?.body()!!.string()
            //현재 보안을 위해서 우선은 공유저장소에 들어있는 값(현재 스마트폰의 저장 정보)과 서버에 저장되어 있는 값을 비교//
            //만약 다를 시 해커가 우회에서 들어올 수 있으므로 로그인 화면으로 이동하여 다시 정상적으로 토큰등을 발급받게 함.//

            val profileRequest: UserProfile = Gson().fromJson(responseData, UserProfile::class.java)

            val sUserId:String = profileRequest.userProfileResult.userId

            if(sUserId.equals(userId)){
                if(activity != null){
                    activity.runOnUiThread(object :Runnable{
                        override fun run(){
                            val loginAuth = profileRequest.userProfileResult.authLogin
                            if(loginAuth.equals("1")) // 로그인 한 경우
                            {
                                // 메인화면으로 이동전 공유저장소에 내용을 최신 정보로 변경
                                PManager.setUserName(profileRequest.userProfileResult.userName)
                                PManager.setUserProfileImageUrl(profileRequest.userProfileResult.userProfileImageUrl)
                                PManager.setUserEmail(profileRequest.userProfileResult.userEmail)
                                PManager.setUserId(profileRequest.userProfileResult.userId)

                                var intent = Intent(activity,MainActivity::class.java)
                                activity.startActivity(intent)

                                activity.finish()
                            } else if(loginAuth.equals("0")) // 로그아웃한 경우
                                loginPageIntent()
                        }

                    })
                }
            }else
                loginPageIntent()
        }
    }

    // 자동 로그인을 확인하기 위한 스레드 시간 벌기
    override fun autoLogin() {
        activity.runOnUiThread(Runnable{
            mHandler.postDelayed(Runnable {
                //최초 앱을 실행 시 로그인이 되어있는지 검사//
                isLoginCheck()
            }, 1500)
        })
    }

    // 공유저장소에 데이터 값이 있으면 바로 서버로 프로필 달라고 하기, 없으면 로그인 페이지
    override fun isLoginCheck() {
        when(userId){
            // 유저가 현재공유저장소에 값이 있는지를 비교
            ""->{
                //profileData.getProfile(userId, requestProfileCallback, activity)
                loginPageIntent()
            }
            else->{
                profileData.getProfile(userId, requestProfileCallback, activity)
            }
        }
    }

    fun loginPageIntent(){
        var intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)

        activity.finish()
    }



}