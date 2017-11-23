package com.kotlin.ourmemories.data.source.memory

/**
 * Created by kimmingyu on 2017. 11. 24..
 */
class MemoryRepository:MemorySource {
    private val memoryRemoteDataSource = MemoryRemoteDataSource
    private val memoryLocalDataSource = MemoryLocalDataSource
}