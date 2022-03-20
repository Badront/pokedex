package com.badront.pokedex.pokemon.details.presentation.model

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.model.LoadingState

internal class DetailedPokemonUiModel(
    val loadingState: LoadingState,
    val header: Header? = null,
    val detailedList: List<PokemonDetailsUiModel> = emptyList()
) {
    class Header(
        val name: String,
        val number: String,
        val image: String,
        val colorPalette: ColorPalette
    )
}