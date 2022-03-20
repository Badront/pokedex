package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel

internal interface PokemonDetailsTypeUiMapper {
    fun map(type: PokemonType): PokemonDetailsUiModel.Types.Type
}