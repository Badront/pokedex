package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel

internal interface DetailedPokemonHeaderUiModelMapper {
    fun map(model: Pokemon): DetailedPokemonUiModel.Header
}