package com.dinesh.tiktok.domain.model

data class Count(
    val _id: String,
    val like_count: Int,
    val video_comment_count: Int,
    val view: Int
)