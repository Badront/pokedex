package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel

internal interface PokemonDetailsUiModelMapper {
    fun map(model: PokemonDetails, evolutionChain: EvolutionChain?): List<PokemonDetailsUiModel>
}