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

    // JSON configuration using kotlinx.serialization
    // This tells the parser to ignore unknown keys from the API response
    val json = Json {
        ignoreUnknownKeys = true
    }

    // Logging interceptor to log HTTP request and response bodies
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttp client with custom timeouts and logging
    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)   // Max time to connect to server
        .readTimeout(30, TimeUnit.SECONDS)      // Max time to read data
        .writeTimeout(30, TimeUnit.SECONDS)     // Max time to send data
        .addInterceptor(loggingInterceptor)     // Adds the logging interceptor
        .build()

    // Retrofit instance configured with:
    // - Base URL of the API
    // - Custom OkHttp client
    // - JSON converter (kotlinx.serialization)
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)                              // Base URL for the API
            .client(client)                                             // Custom HTTP client
            .addConverterFactory(json.asConverterFactory(               // JSON converter
                "application/json".toMediaType()
            ))
            .build()
    }

    // Actual implementation of the API interface
    // This is where API functions (GET, POST, etc.) are defined
    val triviaApi: TriviaApiService = retrofit.create(TriviaApiService::class.java)
}
