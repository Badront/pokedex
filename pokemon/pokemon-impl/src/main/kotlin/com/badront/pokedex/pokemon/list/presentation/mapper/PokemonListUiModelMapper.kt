package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState

internal interface PokemonListUiModelMapper {
    fun map(state: PokemonListViewModel.State): PokemonListUiState
}