package com.badront.pokedex.core.model

sealed class Either<out T, out E : Throwable> {
    class Success<T, E : Throwable> internal constructor(val value: T) : Either<T, E>()
    class Failure<T, E : Throwable> internal constructor(val cause: E) : Either<T, E>()

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
        fun <T, E : Throwable> success(value: T): Either<T, E> {
            return Success(value)
        }

        @JvmStatic
        fun <T, E : Throwable> failure(cause: E): Either<T, E> {
            return Failure(cause)
        }
    }
}

inline fun <T, E : Throwable> Either<T, E>.doOnSuccess(block: (T) -> Unit): Either<T, E> {
    if (isSuccess) {
        block((this as Either.Success).value)
    }
    return this
}

inline fun <T, E : Throwable> Either<T, E>.doOnFailure(block: (E) -> Unit): Either<T, E> {
    if (isFailure) {
        block((this as Either.Failure).cause)
    }
    return this
}

inline fun <R, T, E : Throwable> Either<T, E>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: E) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess((this as Either.Success<T, E>).value)
        else -> onFailure(exception)
    }
}

inline fun <R, T, E : Throwable> Either<T, E>.map(transform: (value: T) -> R): Either<R, E> {
    return when {
        isSuccess -> Either.success(transform((this as Either.Success).value))
        else -> Either.failure((this as Either.Failure).cause)
    }
}