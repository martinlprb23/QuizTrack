package com.roblescode.quiztrack.domain.repository

import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.utils.Response

interface TriviaRepository {

    suspend fun getPlaylists(token: String): Response<List<Playlist>>

    suspend fun startGame(uid: String, token: String, playlistId: String): Response<GameData>

}