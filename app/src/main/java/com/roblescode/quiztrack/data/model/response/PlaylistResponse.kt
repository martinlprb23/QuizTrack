package com.roblescode.quiztrack.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    val success: Boolean,
    val data: List<Playlist>
)


@Serializable
data class Playlist(
    @SerialName("id")
    val id: Long,
    val title: String,
    @SerialName("picture_medium")
    val picture: String
)
