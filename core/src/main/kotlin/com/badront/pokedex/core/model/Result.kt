package com.badront.pokedex.core.model

sealed class Result<out T, out E : Throwable> {
    class Success<T, E : Throwable> internal constructor(val value: T) : Result<T, E>()
    class Failure<T, E : Throwable> internal constructor(val cause: E) : Result<T, E>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? {
        return when {
            isSuccess -> (this as Success<T, E>).value
            else -> null
        }
    }

    fun exceptionOrNull(): E? {
        return when {
            isFailure -> (this as Failure<T, E>).cause
            else -> null
        }
    }

    companion object {
        @JvmStatic
        fun <T, E : Throwable> success(value: T): Result<T, E> {
            return Success(value)
        }

        @JvmStatic
        fun <T, E : Throwable> failure(cause: E): Result<T, E> {
            return Failure(cause)
        }
    }
}

inline fun <T, E : Throwable> Result<T, E>.doOnSuccess(block: (T) -> Unit): Result<T, E> {
    if (isSuccess) {
        block((this as Result.Success).value)
    }
    return this
}

inline fun <T, E : Throwable> Result<T, E>.doOnFailure(block: (E) -> Unit): Result<T, E> {
    if (isFailure) {
        block((this as Result.Failure).cause)
    }
    return this
}

inline fun <R, T, E : Throwable> Result<T, E>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: E) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess((this as Result.Success<T, E>).value)
        else -> onFailure(exception)
    }
}