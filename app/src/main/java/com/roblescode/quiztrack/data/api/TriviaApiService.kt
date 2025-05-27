package com.roblescode.quiztrack.data.api

import com.roblescode.quiztrack.data.constant.ApiConstant
import com.roblescode.quiztrack.data.model.LoginRequest
import com.roblescode.quiztrack.data.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TriviaApiService {

    @POST(ApiConstant.LOGIN)
    suspend fun login(@Body request: LoginRequest): AuthResponse
}