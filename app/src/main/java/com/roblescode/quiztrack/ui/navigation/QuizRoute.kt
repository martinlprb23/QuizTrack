package com.roblescode.quiztrack.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
data class QuizRoute(
    val playlistId: Long,
    val coverImageUrl: String,
)