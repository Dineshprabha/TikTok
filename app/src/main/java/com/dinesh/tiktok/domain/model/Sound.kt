package com.dinesh.tiktok.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sound(
    val _id: String,
    val audio_path: AudioPath,
    val created: String,
    val description: String,
    val id: Int,
    val section: String,
    val sound_name: String,
    val thum: String
) : Parcelable