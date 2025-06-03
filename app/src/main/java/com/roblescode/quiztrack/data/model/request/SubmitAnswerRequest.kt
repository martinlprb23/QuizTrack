package com.roblescode.quiztrack.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SubmitAnswerRequest(
    val sessionId: String,
    val answer: Answer
)

@Serializable
data class Answer(
    val questionId: Long,
    val selectedOptionId: Long
)