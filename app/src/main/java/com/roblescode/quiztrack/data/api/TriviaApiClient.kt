package com.roblescode.quiztrack.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.roblescode.quiztrack.data.constant.ApiConstant
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object TriviaApiClient {

    val json = Json {
        ignoreUnknownKeys = true
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val triviaApi = retrofit.create(TriviaApiService::class.java)

}