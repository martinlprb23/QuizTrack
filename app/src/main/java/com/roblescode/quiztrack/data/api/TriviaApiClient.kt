package com.roblescode.quiztrack.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.roblescode.quiztrack.data.constant.ApiConstant
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object TriviaApiClient {

    val json = Json {
        ignoreUnknownKeys = true
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val triviaApi: TriviaApiService = retrofit.create(TriviaApiService::class.java)

}