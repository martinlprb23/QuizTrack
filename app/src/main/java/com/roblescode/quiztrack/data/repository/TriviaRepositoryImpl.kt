package com.roblescode.quiztrack.data.repository

import android.util.Log
import com.roblescode.quiztrack.data.api.TriviaApiService
import com.roblescode.quiztrack.data.model.request.StartGameRequest
import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.TriviaRepository

class TriviaRepositoryImpl(
    private val triviaApi: TriviaApiService
) : TriviaRepository {

    companion object {
        const val TAG = "TriviaRepository"
    }

    override suspend fun getPlaylists(token: String): Response<List<Playlist>> {
        return try {
            val response = triviaApi.getPlaylist("Bearer $token")
            if (response.success) {
                val playlists = response.data
                Response.Success(playlists)
            } else {
                throw Exception("Failed to get playlist")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Response.Failure(e)
        }
    }

    override suspend fun startGame(
        uid: String,
        token: String,
        playlistId: String
    ): Response<GameData> {
        return try {
            val request = StartGameRequest(playlistId = playlistId, uid = token)
            val response = triviaApi.startGame("Bearer $token", request)

            if (response.success && response.data != null) {
                return Response.Success(response.data)
            } else {
                throw Exception("Failed to start game")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Response.Failure(e)
        }
    }
}