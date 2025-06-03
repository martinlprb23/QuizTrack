package com.roblescode.quiztrack.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val success: Boolean,
    val data: GameData? = null,
)


@Serializable
data class GameData(
    val currentScore: Int = 0,
    val correctAnswers: Int = 0,
    val sessionId: String,
    val questions: List<Question>,
    val playlistId: Long,
)


@Serializable
data class Question(
    val trackId: Long,
    val previewUrl: String,
    val options: List<Option> = emptyList()
)


@Serializable
data class Option(
    val id: Long,
    val text: String,
)
