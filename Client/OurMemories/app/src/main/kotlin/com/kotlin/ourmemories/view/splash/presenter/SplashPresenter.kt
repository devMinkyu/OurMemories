package com.kotlin.ourmemories.view.splash.presenter

import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AlertDialog
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.manager.networkmanager.NManager
import com.kotlin.ourmemories.view.login.LoginActivity
import com.kotlin.ourmemories.view.splash.SplashActivity
import okhttp3.*
import java.io.IOException

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
class SplashPresenter: SplashContract.Presenter {
    lateinit override var mHandler: Handler
    lateinit override var activity:SplashActivity

    var userId:String
    // 서버 전송시 callBack 받는 부분
    private val requestProfileCallBack:Callback = object :Callback{
        // 실패 했을 경우
        override fun onFailure(call: Call?, e: IOException?) {
            if(activity != null){
                activity.runOnUiThread(Runnable {
                    val alertDialog = AlertDialog.Builder(activity)
                    alertDialog.setTitle("Login")
                            .setMessage("요청에러 (네트워크 상태를 점검해주세요)")
                            .setCancelable(false)
                            .setPositiveButton("확인", object: DialogInterface.OnClickListener{
                                override fun onClick(dialog: DialogInterface?, which: Int) {}
                            })
                    val alert = alertDialog.create()
                    alert.show()
                })
            }
        }
        // 성공 했을 경우
        override fun onResponse(call: Call?, response: Response?) {
            val responseData:String = response?.body()!!.string()
        }

    }
    init {
        PManager.init()
        mHandler = Handler(Looper.getMainLooper())
        userId = PManager.getUserId()
    }


    override fun autoLogin() {
        activity.runOnUiThread(Runnable{
            mHandler.postDelayed(Runnable {
                //최초 앱을 실행 시 로그인이 되어있는지 검사//
                isLoginCheck()
            }, 1500)
        })
    }

    override fun isLoginCheck() {
        when(userId){
            // 유저가 현재공유저장소에 값이 있는지를 비교
            ""->{
                //getProfile(userId)
                var intent = Intent(activity, LoginActivity::class.java)
                activity.startActivity(intent)

                activity.finish()
            }
            else->{
                getProfile(userId)
            }
        }
    }

    override fun getProfile(userId: String) {
        // 네트워크 설정
        NManager.init()

        val client = NManager.getClinet()

        // POST방식의 프로토콜 사용
        var builder = HttpUrl.Builder()

        builder.scheme("http")
        //builder.host(activity.resources.getString(R.string.server_domain))
        builder.host("127.0.0.1")
        builder.port(8000)


        // Body 설정
        val formBuilder = FormBody.Builder().add("userId", userId)

        // RequestBody 설정(파일 설정 시 Multipart로 설정)
        val body: RequestBody = formBuilder.build()

        val request:Request = Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(activity)
                .build()

        client!!.newCall(request).enqueue(requestProfileCallBack)
    }

}