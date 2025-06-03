package com.roblescode.quiztrack.data.repository

import android.util.Log
import com.roblescode.quiztrack.data.api.TriviaApiService
import com.roblescode.quiztrack.data.model.request.Answer
import com.roblescode.quiztrack.data.model.request.StartGameRequest
import com.roblescode.quiztrack.data.model.request.SubmitAnswerRequest
import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.model.response.SubmitAnswerData
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.TriviaRepository
import retrofit2.HttpException

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
        playlistId: Long
    ): Response<GameData> {
        return try {
            val request = StartGameRequest(playlistId = playlistId, uid = token)
            val response = triviaApi.startGame("Bearer $token", request)

            if (response.success && response.data != null) {
                Response.Success(response.data)
            } else {
                Response.Failure(Exception("Failed to start game"))
            }
        } catch (e: HttpException) {
            if (e.code() == 400) {
                resumeGame(token)
            } else {
                Log.e(TAG, "HTTP error starting game: ${e.message}", e)
                Response.Failure(e)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting game: ${e.message}", e)
            Response.Failure(e)
        }
    }

    override suspend fun resumeGame(token: String): Response<GameData> {
        return try {
            val response = triviaApi.resumeGame("Bearer $token")

            if (response.success && response.data != null) {
                Response.Success(response.data)
            } else {
                Response.Failure(Exception("Failed to resume game"))
            }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error starting game: ${e.message}", e)
            Response.Failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Error resuming game: ${e.message}", e)
            Response.Failure(e)
        }
    }

    override suspend fun submitAnswer(
        token: String,
        sessionId: String,
        answer: Answer,
    ): Response<SubmitAnswerData> {
        return try {
            val request = SubmitAnswerRequest(
                sessionId = sessionId,
                answer = answer
            )

            val response = triviaApi.submitAnswer("Bearer $token", request)

            if (response.success && response.data != null) {
                Response.Success(response.data)
            } else {
                Response.Failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error starting game: ${e.message}", e)
            Response.Failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Error submitting answer: ${e.message}", e)
            Response.Failure(e)
        }
    }
}