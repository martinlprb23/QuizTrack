package com.roblescode.quiztrack.domain.utils

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AudioPlayer(context: Context) {

    private val _isPlayingFlow = MutableStateFlow(false)
    val isPlayingFlow: StateFlow<Boolean> = _isPlayingFlow

    private var exoPlayer: ExoPlayer? = ExoPlayer.Builder(context).build().apply {
        addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlayingFlow.value = isPlaying
            }
        })
    }

    fun play(outputPath: String) {
        exoPlayer?.apply {
            stop()
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(outputPath))
            prepare()
            play()
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun stop() {
        exoPlayer?.stop()
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        _isPlayingFlow.value = false
    }
}