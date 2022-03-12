package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState

internal interface PokemonListUiModelMapper {
    fun map(state: PokemonListViewModel.State, pokemonColorPalettes: Map<Int, ColorPalette>): PokemonListUiState
}