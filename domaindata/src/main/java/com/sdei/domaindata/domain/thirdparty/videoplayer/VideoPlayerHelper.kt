package com.sdei.domaindata.domain.thirdparty.videoplayer

import com.google.android.exoplayer2.ui.StyledPlayerView

interface VideoPlayerHelper {
    fun initPlayer(url:String, playerView : StyledPlayerView)
    fun onStop()
    fun onPause()
    fun onResume()
    fun onStart()
}