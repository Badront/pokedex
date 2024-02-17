package com.badront.pokedex.core.ext.kotlin

@Suppress("NOTHING_TO_INLINE")
inline fun <T> lazyUnsynchronized(noinline initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)