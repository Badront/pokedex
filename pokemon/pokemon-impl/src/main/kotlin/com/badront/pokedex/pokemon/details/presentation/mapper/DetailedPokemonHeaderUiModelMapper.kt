package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel

internal interface DetailedPokemonHeaderUiModelMapper {
    fun map(model: Pokemon, palette: ColorPalette?): DetailedPokemonUiModel.Header
}