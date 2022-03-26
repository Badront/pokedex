package com.badront.pokedex.pokemon.core.domain.model

class PokemonSpecies(
    val id: PokeId,
    val name: String,
    val evolvesFrom: Pokemon?,
    val evolutionChainId: Int?
)