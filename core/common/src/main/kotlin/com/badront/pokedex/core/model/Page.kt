package com.badront.pokedex.core.model

class Page<T>(
    val total: Int,
    val items: List<T>
)