package com.kotlin.ourmemories.view.place.detail

/**
 * Created by hee on 2017-11-28.
 */
import java.util.*

/**
 * Created by hee on 2017-11-27.
 */
data class RecomData (
        val name:String ,
        val storename1:String,
        val storeaddress1:String,
        val storename2:String,
        val storeaddress2:String,
        val storename3:String,
        val storeaddress3:String
)

data class GsonData(val data:ArrayList<RecomData>)