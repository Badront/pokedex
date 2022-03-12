package com.badront.pokedex.pokemon.list.presentation.model

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette

internal sealed class PokemonListUiModel {
    data class Pokemon(
        val id: Int,
        val number: String,
        val name: String,
        val image: String,
        val palette: ColorPalette?
    ) : PokemonListUiModel()

    object NextPageLoading : PokemonListUiModel()
    object NextPageLoadingError : PokemonListUiModel()
}