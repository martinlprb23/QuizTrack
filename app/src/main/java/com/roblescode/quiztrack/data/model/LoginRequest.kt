package com.roblescode.quiztrack.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val idToken: String
)
