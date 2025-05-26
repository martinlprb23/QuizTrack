package com.roblescode.quiztrack.data.utils

/**
 * A sealed class that represents the result of an operation.
 * It helps model different states of a request in a type-safe way.
 *
 * @param T The type of data returned on success.
 */
sealed class Response<out T> {

    /**
     * Represents a loading state.
     * Use this when the operation is still in progress.
     */
    data object Loading : Response<Nothing>()

    /**
     * Represents a successful result of the operation.
     *
     * @param data The result data returned when the operation succeeds.
     */
    data class Success<out T>(val data: T) : Response<T>()

    /**
     * Represents a failed result of the operation.
     *
     * @param exception The exception that caused the failure. It can be null.
     */
    data class Failure(val exception: Exception?) : Response<Nothing>()
}
