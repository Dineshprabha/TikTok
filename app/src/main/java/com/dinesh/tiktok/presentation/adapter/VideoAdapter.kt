package com.dinesh.tiktok.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.tiktok.R
import com.dinesh.tiktok.domain.model.Msg
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope

class VideoAdapter(
    var videos: List<Msg>,
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var exoPlayer: ExoPlayer? = null
    private var currentPlayingPosition: Int = -1
    private var previousPlayingPosition: Int = -1

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val exoPlayerView: PlayerView = itemView.findViewById(R.id.exoPlayerView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)

        fun bind(video: Msg) {

            titleTextView.text = video.user_info.username

            // Load thumbnail (use Glide or similar library)
            Glide.with(thumbnailImageView.context)
                .load(video.thum)
                .into(thumbnailImageView)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = videos.size


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)

    }

    fun stopCurrentVideo() {
        exoPlayer?.release()
        exoPlayer = null
        previousPlayingPosition = currentPlayingPosition
        currentPlayingPosition = -1
    }

    private fun cacheNextAndPreviousVideos(position: Int) {
        exoPlayer?.let { player ->
            val nextPosition = position + 1
            if (nextPosition < videos.size) {
                val nextMediaItem = MediaItem.fromUri(videos[nextPosition].video)
                player.addMediaItem(nextMediaItem)
            }

            val previousPosition = position - 1
            if (previousPosition >= 0) {
                val previousMediaItem = MediaItem.fromUri(videos[previousPosition].video)
                player.addMediaItem(0, previousMediaItem)
            }

            player.prepare()
        }
    }
}