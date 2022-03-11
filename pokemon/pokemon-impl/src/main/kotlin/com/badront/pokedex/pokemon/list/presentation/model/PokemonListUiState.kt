package com.badront.pokedex.pokemon.list.presentation.model

internal class PokemonListUiState(
    val isRefreshing: Boolean,
    val content: Content
) {
    sealed class Content {
        object Error : Content()
        object Loading : Content()
        class Data(val items: List<PokemonListUiModel>) : Content()
    }
}