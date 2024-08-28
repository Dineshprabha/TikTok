package com.dinesh.tiktok.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.tiktok.data.repository.VideoRepository
import com.dinesh.tiktok.domain.model.Msg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _video = MutableStateFlow<List<Msg>>(emptyList())
    val video : StateFlow<List<Msg>> = _video

    init {
        fetchVideos()
    }

    fun fetchVideos(){
        viewModelScope.launch {
            videoRepository.getVideos().collect{
                _video.value = it
            }
        }
    }
}