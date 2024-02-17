package com.badront.pokedex.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface AppDispatchers {
    val ui: MainCoroutineDispatcher
    val io: CoroutineDispatcher
    val computing: CoroutineDispatcher
}