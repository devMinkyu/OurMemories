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
import android.app.ProgressDialog
import com.kotlin.ourmemories.data.source.login.LoginRepository
import com.kotlin.ourmemories.view.MainActivity


/**
 * Created by kimmingyu on 2017. 11. 1..
 */
class LoginActivity : AppCompatActivity(){
    private lateinit var presenter:LoginContract.Presenter
    private lateinit var mDialog: ProgressDialog

    companion object {

        val START_DELAY = 300
        val ANIM_ITME_DURATION = 1000
        val ITEM_DELAY = 300
    }
    var animationStarted:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mDialog = ProgressDialog(this)
        mDialog.setMessage("Please wait...")
        mDialog.setCancelable(false)

        // presenter 연결부분
        presenter = LoginPresenter().apply {
            activity = this@LoginActivity
            mLoginManager = LoginManager.getInstance()
            callbackManager = CallbackManager.Factory.create()
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
                showpDialog()
                presenter.facebookLogin()
            }
        }

        // 카카오톡 로그인 버튼 눌렀을 때(우선 메인 액티비티로 가는 버튼
        kakao_login_button.setOnClickListener{
            val intent:Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(!hasFocus or animationStarted){
            return
        }
        presenter.animation()
        super.onWindowFocusChanged(hasFocus)
    }

    // facebook Login 인증에 대한 결과를 받아오는 곳
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 등록이 되어 있어야지 정상적으로 onSuccess에서 정보를 받을 수 있다
        presenter.callbackManager.onActivityResult(requestCode, resultCode, data)
    }

   fun showpDialog() {
        if (!mDialog.isShowing())
            mDialog.show()
    }

    fun hidepDialog() {
        if (mDialog.isShowing())
            mDialog.dismiss()
    }
}



