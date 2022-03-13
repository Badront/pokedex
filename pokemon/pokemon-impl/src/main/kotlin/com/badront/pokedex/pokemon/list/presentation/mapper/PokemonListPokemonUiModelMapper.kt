package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal interface PokemonListPokemonUiModelMapper {
    fun map(model: Pokemon, palette: ColorPalette?): PokemonListUiModel.Pokemon
}