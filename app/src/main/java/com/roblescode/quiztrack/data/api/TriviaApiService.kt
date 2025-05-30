package com.roblescode.quiztrack.data.api

import com.roblescode.quiztrack.data.constant.ApiConstant
import com.roblescode.quiztrack.data.model.request.StartGameRequest
import com.roblescode.quiztrack.data.model.response.GameResponse
import com.roblescode.quiztrack.data.model.response.PlaylistResponse
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

}