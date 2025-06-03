package com.roblescode.quiztrack.data.api

import com.roblescode.quiztrack.data.constant.ApiConstant
import com.roblescode.quiztrack.data.model.request.StartGameRequest
import com.roblescode.quiztrack.data.model.request.SubmitAnswerRequest
import com.roblescode.quiztrack.data.model.response.GameResponse
import com.roblescode.quiztrack.data.model.response.PlaylistResponse
import com.roblescode.quiztrack.data.model.response.SubmitAnswerResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TriviaApiService {

    @GET(ApiConstant.PLAYLIST)
    suspend fun getPlaylist(
        @Header("Authorization") token: String
    ): PlaylistResponse

    @POST(ApiConstant.START_GAME)
    suspend fun startGame(
        @Header("Authorization") token: String,
        @Body request: StartGameRequest
    ): GameResponse

    @GET(ApiConstant.RESUME_GAME)
    suspend fun resumeGame(
        @Header("Authorization") token: String
    ): GameResponse

    @POST(ApiConstant.SUBMIT_ANSWER)
    suspend fun submitAnswer(
        @Header("Authorization") token: String,
        @Body request: SubmitAnswerRequest
    ): SubmitAnswerResponse

}