package com.badront.pokedex.core.ext.url

fun String.lastUrlPathSegment(): String {
    val changed = if (endsWith("/")) {
        dropLast(1)
    } else {
        this
    }
    return changed.split("/").last()
}