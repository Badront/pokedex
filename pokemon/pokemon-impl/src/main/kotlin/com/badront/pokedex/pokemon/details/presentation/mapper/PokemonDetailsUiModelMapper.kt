package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel

internal interface PokemonDetailsUiModelMapper {
    fun map(model: PokemonDetails, palette: ColorPalette?): List<PokemonDetailsUiModel>
}