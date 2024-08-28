package com.dinesh.tiktok.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.tiktok.R
import com.dinesh.tiktok.databinding.ActivityMainBinding
import com.dinesh.tiktok.presentation.adapter.VideoAdapter
import com.dinesh.tiktok.presentation.viewmodel.VideoViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var exoPlayer: ExoPlayer? = null
    private var currentPlayingPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        videoAdapter = VideoAdapter(emptyList())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = videoAdapter

        lifecycleScope.launch {
            viewModel.video.collect { videos ->
                videoAdapter = VideoAdapter(videos)
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    playVideoOnScreen(recyclerView)
                }
            }
        })
    }

    private fun playVideoOnScreen(recyclerView: RecyclerView) {
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        // Find the center item
        val centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        if (centerPosition != currentPlayingPosition) {
            currentPlayingPosition = centerPosition
            val holder = recyclerView.findViewHolderForAdapterPosition(centerPosition) as? VideoAdapter.VideoViewHolder
            holder?.let {
                exoPlayer?.release()

                exoPlayer = ExoPlayer.Builder(this).build().apply {
                    val mediaItem = MediaItem.fromUri(videoAdapter.videos[centerPosition].video)
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = true
                }
                it.exoPlayerView.player = exoPlayer

                // Preload next and previous videos
                cacheNextAndPreviousVideos(centerPosition)
            }
        }
    }

    private fun cacheNextAndPreviousVideos(position: Int) {
        exoPlayer?.let { player ->
            val nextPosition = position + 1
            if (nextPosition < videoAdapter.itemCount) {
                val nextMediaItem = MediaItem.fromUri(videoAdapter.videos[nextPosition].video)
                player.addMediaItem(nextMediaItem)
            }

            val previousPosition = position - 1
            if (previousPosition >= 0) {
                val previousMediaItem = MediaItem.fromUri(videoAdapter.videos[previousPosition].video)
                player.addMediaItem(0, previousMediaItem)
            }

            player.prepare()
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }


}