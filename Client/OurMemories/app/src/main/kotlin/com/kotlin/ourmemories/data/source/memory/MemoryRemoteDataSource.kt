package com.kotlin.ourmemories.data.source.memory

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.PManager
import com.kotlin.ourmemories.manager.networkmanager.NManager
import okhttp3.*
import okhttp3.MultipartBody
import java.io.File


/**
 * Created by kimmingyu on 2017. 11. 24..
 */
// 서버랑 연동하여 서버 디비에 저장
object MemoryRemoteDataSource:MemorySource {
    override fun memorySave(id:String, title: String, fromDate: String, toDate: String?, lat: Double, lon: Double, nation: String, text:String, uploadFile: File?, classification: Int, requestMemoryCallback: Callback?, activity: AppCompatActivity) {
        // 네트워크 설정
        NManager.init()
        PManager.init()
        val client = NManager.getClinet()

        // POST방식의 프로토콜 사용
        var builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(activity.resources.getString(R.string.server_domain))
        builder.port(8000)
        builder.addPathSegment("memory")


        /** 파일 전송이므로 MultipartBody 설정 **/
        val multipartBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", PManager.getUserId())
                .addFormDataPart("memoryTitle", title)
                .addFormDataPart("memoryFromDate", fromDate)
                .addFormDataPart("memoryToDate", toDate)
                .addFormDataPart("memoryLatitude", lat.toString())
                .addFormDataPart("memoryLongitude", lon.toString())
                .addFormDataPart("memoryNation", title)
                .addFormDataPart("memoryClassification", title)
                .addFormDataPart("text", text)

        // 사진,동영상 선택 여부
        when{
            uploadFile.toString().contains("jpg")->{
                //파일 전송을 위한 설정.//
                val mediaType = MediaType.parse("image/jpeg")
                multipartBuilder.addFormDataPart("uploadFile", uploadFile!!.name, RequestBody.create(mediaType, uploadFile))
            }
            uploadFile.toString().contains("mp4")->{
                //파일 전송을 위한 설정.//
                val mediaType = MediaType.parse("video/mp4")
//                    val mediaType = MediaType.parse("video/mpeg")
                multipartBuilder.addFormDataPart("uploadFile", uploadFile!!.name, RequestBody.create(mediaType, uploadFile))
            }
        }


        // RequestBody 설정(파일 설정 시 Multipart 로 설정)
        val body: RequestBody = multipartBuilder.build()

        // Request 설정
        val request: Request = Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(activity)
                .build()

        // 비동기 방식(enqueue)으로 Callback 구현
        client!!.newCall(request).enqueue(requestMemoryCallback)

    }
}
