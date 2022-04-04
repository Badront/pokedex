package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.badront.pokedex.pokemon.core.widget.PokemonTypeUiModel

internal interface PokemonDetailsTypeUiMapper {
    fun map(type: PokemonType): PokemonTypeUiModel
}