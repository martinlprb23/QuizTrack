package com.roblescode.quiztrack.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SubmitAnswerResponse(
    val success: Boolean,
    val data: SubmitAnswerData? = null,
    val message: String? = null
)

@Serializable
data class SubmitAnswerData(
    val isComplete: Boolean,
    val score: Int? = null,
    val currentScore: Int? = null,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val answeredQuestions: Int? = null,
    val isNewHighScore: Boolean? = null
)
