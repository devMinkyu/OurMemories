package com.kotlin.ourmemories.view.place.presenter

import android.content.Context
import android.support.v4.app.Fragment
import com.google.gson.Gson
import com.kotlin.ourmemories.data.jsondata.AdministrativeDistrict
import com.kotlin.ourmemories.view.place.adapter.data.SiDo
import com.kotlin.ourmemories.view.place.adapter.data.SiGunGu
import java.io.InputStreamReader

/**
 * Created by hee on 2017-11-07.
 */
class PlacePresenter:PlaceContract.Presenter {
    lateinit override var fragment: Fragment
    lateinit override var mView: PlaceContract.View
    lateinit override var mContext: Context

    override fun getCityDate() {
        // json 데이터를 가져오는 부분
        val inputStream = InputStreamReader(fragment.activity.assets.open("admin.json"))
        val admin: AdministrativeDistrict = Gson().fromJson(inputStream, AdministrativeDistrict::class.java)

        var siDo = mutableListOf<SiDo>()
        for(i in 0 until admin.data.size){
            var siGunGu = mutableListOf<SiGunGu>()
            for(j in 0 until admin.data[i].sub.size){
                siGunGu.add(j, SiGunGu(admin.data[i].sub[j]))
            }
            siDo.add(i, SiDo(admin.data[i].city, siGunGu))
        }

        mView.updateCityView(siDo)
    }

}