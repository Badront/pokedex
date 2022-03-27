package com.badront.pokedex.pokemon.list.presentation.model

internal sealed class PokemonListUiModel {
    data class Pokemon(
        val id: Int,
        val number: String,
        val name: String,
        val image: String
    ) : PokemonListUiModel()

    object NextPageLoading : PokemonListUiModel()
    object NextPageLoadingError : PokemonListUiModel()
}