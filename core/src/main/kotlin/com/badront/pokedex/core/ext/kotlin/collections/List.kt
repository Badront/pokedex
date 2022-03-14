package com.badront.pokedex.core.ext.kotlin.collections

fun <T> List<T>.areContentsTheSame(second: List<T>): Boolean {
    if (size != second.size) {
        return false
    }

    forEachIndexed { index, value ->
        val secondValue = second[index]
        var isCurrentEquals = true
        when {
            value is List<*> && secondValue is List<*> -> if (!value.areContentsTheSame(secondValue as List<*>)) {
                isCurrentEquals = false
            }
            value is Array<*> && secondValue is Array<*> -> if (!value.contentDeepEquals(secondValue as Array<*>)) {
                isCurrentEquals = false
            }
            secondValue != value -> isCurrentEquals = false
        }
        if (isCurrentEquals.not()) {
            return false
        }
    }
    return true
}