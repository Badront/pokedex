package com.badront.pokedex.pokemon.core.domain.model

class PokemonDetails(
    val id: PokeId,
    val height: Int?,
    val weight: Int?,
    val types: List<PokemonType>
)