package com.dinesh.tiktok.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioPath(
    val acc: String,
    val mp3: String
) : Parcelable