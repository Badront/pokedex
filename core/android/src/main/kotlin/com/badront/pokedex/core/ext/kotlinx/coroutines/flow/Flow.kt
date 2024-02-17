package com.badront.pokedex.core.ext.kotlinx.coroutines.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.observe(lifecycle: LifecycleOwner, crossinline block: suspend (T) -> Unit) {
    lifecycle.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                block(it)
            }
        }
    }
}

fun <T> Flow<T?>.filterNotNull(): Flow<T> = transform { value ->
    if (value != null) return@transform emit(value)
}
