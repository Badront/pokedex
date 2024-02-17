package com.badront.pokedex.core.ext.kotlin.collections

/**
 * Filters [this], keeping only elements that are not in [excludedElements].
 * @param excludedElements elements to exclude from result list
 * @param predicate when elements from two lists are the same
 */
inline fun <T> Iterable<T>.filterIfNotIn(
    excludedElements: Iterable<T>,
    predicate: (fromList: T, fromExcluded: T) -> Boolean
): List<T> {
    return filter { item ->
        excludedElements.none { excluded ->
            predicate(item, excluded)
        }
    }
}

/**
 * Filters [this], keeping only elements that are in [includedElements].
 * @param includedElements elements to keep in result list
 * @param predicate when elements from two lists are the same
 */
inline fun <T> Iterable<T>.filterIfIn(
    includedElements: Iterable<T>,
    predicate: (fromList: T, fromIncluded: T) -> Boolean
): List<T> {
    return filter { item ->
        includedElements.any { included ->
            predicate(item, included)
        }
    }
}
