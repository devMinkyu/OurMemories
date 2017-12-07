package com.kotlin.ourmemories.data.jsondata

/**
 * Created by kimmingyu on 2017. 12. 6..
 */
data class ReComMemory(val isSuccess:String, val reviewMemoryResult: ArrayList<ReComMemoryResult>?)
data class ReComMemoryResult(val media:String, val title:String, val contents:String, val address:String)