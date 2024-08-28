package com.dinesh.tiktok.data.remote

import com.dinesh.tiktok.domain.model.VideoResponse
import retrofit2.http.GET

interface VideoApi {

    @GET("index.php?p=showAllVideos")
    suspend fun getAllVideos() : VideoResponse
}