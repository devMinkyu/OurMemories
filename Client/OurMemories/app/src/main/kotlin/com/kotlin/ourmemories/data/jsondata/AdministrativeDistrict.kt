package com.kotlin.ourmemories.data.jsondata

/**
 * Created by hee on 2017-12-05.
*/
data class AdministrativeDistrict (val data:ArrayList<cities>)

data class cities(val city:String, val sub:ArrayList<String>)

