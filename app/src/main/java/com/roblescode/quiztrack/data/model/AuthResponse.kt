package com.roblescode.quiztrack.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val success: Boolean,
    val message: String,
)
