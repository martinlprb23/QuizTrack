package com.roblescode.quiztrack.ui.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.roblescode.quiztrack.ui.theme.primary
import kotlinx.coroutines.launch

@Composable
fun PlayAndPause(
    modifier: Modifier = Modifier,
    isAudioPlaying: Boolean,
    playListCover: String?,
    onPlayAudio: () -> Unit,
    onPauseAudio: () -> Unit,
) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .scale(scale.value)
            .size(250.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color.Gray)
            .clickable(onClick = {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.85f, animationSpec = tween(durationMillis = 100)
                    )
                    scale.animateTo(
                        targetValue = 1f, animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }

                if (isAudioPlaying) {
                    onPauseAudio()
                } else {
                    onPlayAudio()
                }
            }), contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = playListCover,
            contentDescription = null,
            modifier = modifier.fillMaxSize()
        )

        Icon(
            imageVector = if (isAudioPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(150.dp)
        )
    }
}
