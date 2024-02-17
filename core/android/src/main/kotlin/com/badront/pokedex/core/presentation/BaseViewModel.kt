package com.badront.pokedex.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<STATE, ACTION, EVENT> : ViewModel() {
    private val _viewStates = MutableStateFlow<STATE?>(null)
    val viewStates: Flow<STATE> = _viewStates.filterNotNull()

    private val _viewActions = MutableSharedFlow<ACTION>(replay = 0)
    val viewActions: Flow<ACTION> = _viewActions

    protected var viewState: STATE
        get() = _viewStates.value ?: throw UninitializedPropertyAccessException("\"viewState\" was not initialized")
        set(value) {
            _viewStates.value = value
        }

    open fun onEvent(event: EVENT) {
        // no action by default
    }

    protected fun sendAction(action: ACTION) {
        viewModelScope.launch { _viewActions.emit(action) }
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onError: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            context = context + createExceptionHandler(onError),
            start = start
        ) {
            block(this)
        }
    }

    /**
     * Throws [Exception] to parent [CoroutineScope] error handler if coroutine finished with [Exception]
     */
    protected fun <T> throwAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        onError: ((Throwable) -> Unit)? = null,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return viewModelScope.async(
            context = context,
            start = start
        ) {
            try {
                block(this)
            } catch (e: Exception) {
                onError?.invoke(e)
                throw e
            }
        }
    }

    /**
     * If coroutine finished with [Exception], returns null and handles [Exception] in [onError]
     */
    protected fun <T> async(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onError: ((Throwable) -> T)? = null,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T?> {
        return viewModelScope.async(
            context = context,
            start = start
        ) {
            try {
                block(this)
            } catch (e: Exception) {
                handleException(e)
                onError?.invoke(e)
            }
        }
    }

    protected fun handleException(throwable: Throwable) {
        // nothing to do here
    }

    protected fun createExceptionHandler(block: ((Throwable) -> Unit)? = null): CoroutineExceptionHandler {
        return BaseCoroutineExceptionHandler(
            callback = { throwable ->
                handleException(throwable)
                block?.invoke(throwable)
            }
        )
    }

    private class BaseCoroutineExceptionHandler(
        private val callback: ((Throwable) -> Unit)?
    ) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            callback?.invoke(exception)
        }
    }
}