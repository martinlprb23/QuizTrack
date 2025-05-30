package com.roblescode.quiztrack.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val success: Boolean,
    val data: GameData? = null,
)


@Serializable
data class GameData(
    val sessionId: String,
    val questions: List<Question>,
    val playlistId: String,
    val totalQuestions: Int
)


@Serializable
data class Question(
    val questionId: Long,
    val previewUrl: String,
    val options: List<Option>
)


@Serializable
data class Option(
    val id: Long,
    val text: String,
)
