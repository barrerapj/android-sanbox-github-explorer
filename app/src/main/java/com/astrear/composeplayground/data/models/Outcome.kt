package com.astrear.composeplayground.data.models

/**
 * Network response states
 * @param T
 */
sealed class Outcome<T> {
    /**
     * Success response
     * @param T
     * @property data T
     * @constructor
     */
    data class Success<T>(val data: T) : Outcome<T>()

    /**
     * Error response
     * @param T
     * @property error Throwable
     * @constructor
     */
    data class Error<T>(val error: Throwable?) : Outcome<T>()
}
