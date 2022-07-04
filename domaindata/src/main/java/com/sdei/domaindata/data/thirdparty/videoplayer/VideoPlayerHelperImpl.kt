package com.sdm.mediacard.data.thirdparty.videoplayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.sdei.domaindata.domain.thirdparty.videoplayer.VideoPlayerHelper
import javax.inject.Inject

class VideoPlayerHelperImpl @Inject constructor(val context: Context) : VideoPlayerHelper {

    private var mediaSource: ProgressiveMediaSource? = null
    private lateinit var mediaSourceFactory:DefaultMediaSourceFactory

    private lateinit var simpleExoPlayer: ExoPlayer
    private lateinit var playerView : StyledPlayerView


    override fun initPlayer(url:String, playerView : StyledPlayerView) {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(context)
        mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            MediaItem.fromUri(url))

        mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)
        this.playerView = playerView

        playWhenReady()
    }

    override fun onStop() {
        releasePlayer()
    }


    override fun onPause() {
        releasePlayer()
    }

    override fun onStart() {
        playWhenReady()
    }

    override fun onResume() {
        playWhenReady()
    }



    private fun playWhenReady() {
        simpleExoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
        mediaSource?.apply {
            simpleExoPlayer.addMediaSource(this)
            simpleExoPlayer.playWhenReady = true
            playerView.player = simpleExoPlayer
        }
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }
}