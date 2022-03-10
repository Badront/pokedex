package com.badront.pokedex.pokemon.list.presentation.model

internal class PokemonListUiState(
    val isRefreshing: Boolean,
    val isLoading: Boolean,
    val items: List<PokemonListUiModel>
)