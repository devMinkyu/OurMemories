package com.kotlin.ourmemories.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.login.presenter.LoginContract
import com.kotlin.ourmemories.view.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import android.graphics.Typeface
import com.kakao.auth.Session
import com.kotlin.ourmemories.data.source.login.LoginRepository
import com.kotlin.ourmemories.view.MainActivity


/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class LoginActivity : AppCompatActivity(){
    private lateinit var presenter:LoginContract.Presenter

    companion object {

        val START_DELAY = 300

        val ANIM_TIME_DURATION = 1000
        val ITEM_DELAY = 300
        val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    }
    private var animationStarted:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setContentView(R.layout.activity_login)

        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        loginTitle.typeface = canaroExtraBold
        loginSubtitle.typeface = canaroExtraBold

        // presenter 연결부분
        presenter = LoginPresenter().apply {
            activity = this@LoginActivity
            mLoginManager = LoginManager.getInstance()
            callbackManager = CallbackManager.Factory.create()
            callback = SessionCallback()
            loginData = LoginRepository()
        }

        presenter.mLoginManager.logOut()

        // 페이스북 로그인 버튼 눌렀을 때
        facebook_login_button.setOnClickListener{
            if(presenter.isLogin()){
                Toast.makeText(this, "이미 로그인 되어있습니다. 로그아웃 해주세요", Toast.LENGTH_SHORT).show()
                presenter.mLoginManager.logOut()
                finish()
            }else{
                showDialog()
                presenter.facebookLogin()
            }
        }

//        // 카카오톡 로그인 버튼 눌렀을 때(우선 메인 액티비티로 가는 버튼
//        kakao_login_button.setOnClickListener{
//            if(presenter.isLogin()){
//                Toast.makeText(this, "이미 로그인 되어있습니다. 로그아웃 해주세요", Toast.LENGTH_SHORT).show()
//                presenter.mLoginManager.logOut()
//                finish()
//            }else{
//                showDialog()
//                presenter.kakaoLogin()
//            }
////            startActivity(Intent(this, MainActivity::class.java))
////            finish()
//        }
    }
    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(presenter.callback)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(!hasFocus or animationStarted){return}
        presenter.animation()
        super.onWindowFocusChanged(hasFocus)
    }

    // facebook Login 인증에 대한 결과를 받아오는 곳
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 등록이 되어 있어야지 정상적으로 onSuccess에서 정보를 받을 수 있다
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return
        }else{
            presenter.callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun showDialog() { animation_view.visibility = View.VISIBLE }
    fun hideDialog() { animation_view.visibility = View.INVISIBLE }


}



