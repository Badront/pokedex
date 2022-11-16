package com.badront.pokedex.pokemon.core.domain.model

data class Pokemon(
    val id: PokeId,
    val name: String,
    val number: Int,
    val image: String
)

typealias PokeId = Int