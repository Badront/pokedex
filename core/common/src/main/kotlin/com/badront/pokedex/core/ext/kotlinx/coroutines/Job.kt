package com.badront.pokedex.core.ext.kotlinx.coroutines

import kotlinx.coroutines.Job

fun Job?.isCompletedOrCanceled(): Boolean {
    return this == null || isCompleted || isCancelled
}