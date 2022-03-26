package com.badront.pokedex.evolution.core.domain.model

import com.badront.pokedex.pokemon.core.domain.model.Pokemon

class EvolutionChain(
    val id: EvolutionChainId,
    val pokemon: Pokemon,
    val details: List<EvolutionDetails>,
    val evolvedTo: List<EvolutionChain>
)

typealias EvolutionChainId = Int