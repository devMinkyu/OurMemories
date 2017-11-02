package com.kotlin.ourmemories.manager.networkmanager

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kotlin.ourmemories.manager.MyApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
// 네트워크 자원 생성, 싱글톤으로 생성
object NManager{
    private var nManager: NetworkManager? = null
    fun init(){
        if (nManager == null){
            nManager = NetworkManager()
        }
    }

    fun getClinet() = nManager?.client
}

class NetworkManager {
    var client:OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val context = MyApplication.context

        // 영속적 쿠키 설정
        val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(context))
        builder.cookieJar(cookieJar)

        val cacheDir = File(context.cacheDir,"network")

        if(!cacheDir.exists())
            cacheDir.mkdir()

        // 쿠기의 세션 만료시간 설정
        val cache = Cache(cacheDir, 10*1024*1024)
        builder.cache(cache)

        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)

        client = builder.build()
    }

}