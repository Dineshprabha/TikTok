package com.dinesh.tiktok.domain.model

data class VideoResponse(
    val code: String,
    val msg: List<Msg>,
    val s: String
)