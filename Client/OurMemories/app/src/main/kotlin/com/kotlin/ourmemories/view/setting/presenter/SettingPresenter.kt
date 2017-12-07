package com.kotlin.ourmemories.view.setting.presenter

import android.content.Context
import android.support.v4.app.Fragment
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.view.login.LoginActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by kimmingyu on 2017. 12. 4..
 */
class SettingPresenter:SettingContract.Presenter {
    lateinit override var fragment: Fragment
    lateinit override var mContext: Context
    init {
        PManager.init()
    }

    override fun logOut() {
        if(PManager.getUserKakaoId() != ""){
            PManager.setUserIsLogin("0")
            fragment.startActivity<LoginActivity>()
            fragment.activity.finish()
        }else{
            PManager.setUserIsLogin("0")
            PManager.setUserKakaoId("")
            UserManagement.requestLogout(object : LogoutResponseCallback(){
                override fun onCompleteLogout() {
                    fragment.startActivity<LoginActivity>()
                    fragment.activity.finish()
                }
            })
        }
    }
}