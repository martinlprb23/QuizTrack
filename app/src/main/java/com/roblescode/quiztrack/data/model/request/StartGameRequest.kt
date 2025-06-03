package com.roblescode.quiztrack.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class StartGameRequest(
    val playlistId: Long,
    val uid: String,
)