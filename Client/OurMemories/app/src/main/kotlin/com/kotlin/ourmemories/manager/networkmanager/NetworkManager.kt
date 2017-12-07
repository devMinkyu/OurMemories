package com.kotlin.ourmemories.manager.networkmanager

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.picasso.OkHttp3Downloader
import com.kotlin.ourmemories.manager.GlobalApplication
import com.squareup.picasso.Picasso
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
    fun gatPicasso() = nManager?.picasso
}

class NetworkManager {
    var client:OkHttpClient
    var picasso:Picasso

    init {
        val builder = OkHttpClient.Builder()
        val context = GlobalApplication.context

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

        //피카소를 okhttp3에 맞추어서 다시 다운로더 작성(이미지 처리 HTTPS issue해결)
        picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(client))
                .build()
    }

}