package com.badront.pokedex.pokemon.core.domain.model

import com.badront.pokedex.evolution.core.domain.model.EvolutionChainId

class PokemonSpecies(
    val id: PokeId,
    val name: String,
    val evolvesFrom: Pokemon?,
    val evolutionChainId: EvolutionChainId?
)