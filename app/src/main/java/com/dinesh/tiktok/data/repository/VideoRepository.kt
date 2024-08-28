package com.dinesh.tiktok.data.repository

import com.dinesh.tiktok.data.remote.VideoApi
import com.dinesh.tiktok.domain.model.Msg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(
    private val videoApi: VideoApi
) {
    fun getVideos() : Flow<List<Msg>> = flow {
        val response = videoApi.getAllVideos()
        emit(response.msg)
    }.flowOn(Dispatchers.IO)

}