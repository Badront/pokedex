package com.badront.pokedex.pokemon.details.presentation.model

import androidx.annotation.ColorInt
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

sealed class PokemonDetailsUiModel {
    class Types(
        val items: List<Type>
    ) : PokemonDetailsUiModel() {
        data class Type(
            val type: PokemonType.Type,
            val name: String,
            @ColorInt val color: Int
        )
    }

    data class Header(
        val text: String,
        @ColorInt val color: Int
    ) : PokemonDetailsUiModel()

    data class Measurements(
        val height: String?,
        val weight: String?
    ) : PokemonDetailsUiModel()

    data class Description(
        val text: String
    ) : PokemonDetailsUiModel()
}