package com.badront.pokedex.core.model

class Page<T>(
    val total: Long,
    val items: List<T>
)