package com.kotlin.ourmemories.manager

import android.app.Activity
import android.app.Application
import android.content.Context
import com.kakao.auth.*

/**
 * Created by kimmingyu on 2017. 12. 8..
 */
class GlobalApplication:Application() {
    companion object {
        private var obj: GlobalApplication? = null
        private var currentActivity:Activity? = null
        lateinit var context:Context

        fun getGlobalApplicationContext(): GlobalApplication {
            return obj!!
        }

    }

    private class KakaoSDKAadpter:KakaoAdapter(){
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun isSaveFormData(): Boolean {
                    return true
                }

                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { getGlobalApplicationContext() }
        }
    }
    override fun onCreate() {
        super.onCreate()
        obj = this
        context = this
        KakaoSDK.init(KakaoSDKAadpter())
    }




}