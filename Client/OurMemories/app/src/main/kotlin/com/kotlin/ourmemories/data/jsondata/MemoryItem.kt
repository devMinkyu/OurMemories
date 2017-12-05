package com.kotlin.ourmemories.data.jsondata

/**
 * Created by kimmingyu on 2017. 11. 29..
 */

data class MemoryItem( val isSuccess:String, val memoryItemResult: MemoryItemResult)
data class MemoryItemResult(val mediaMemory:String, val textMemory:String)