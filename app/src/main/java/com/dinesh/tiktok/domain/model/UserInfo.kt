package com.dinesh.tiktok.domain.model

data class UserInfo(
    val _id: String,
    val fb_id: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val profile_pic: String,
    val username: String,
    val verified: String
)