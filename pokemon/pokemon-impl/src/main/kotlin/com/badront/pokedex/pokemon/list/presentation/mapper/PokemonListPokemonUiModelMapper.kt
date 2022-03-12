package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal interface PokemonListPokemonUiModelMapper {
    fun map(model: ListPokemon, palette: ColorPalette?): PokemonListUiModel.Pokemon
}