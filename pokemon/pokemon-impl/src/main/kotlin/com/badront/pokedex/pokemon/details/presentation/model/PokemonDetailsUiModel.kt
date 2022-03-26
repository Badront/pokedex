package com.badront.pokedex.pokemon.details.presentation.model

import androidx.annotation.ColorInt
import com.badront.pokedex.core.model.StringDesc
import com.badront.pokedex.evolution.widget.model.EvolutionUi
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

sealed class PokemonDetailsUiModel {
    object Loading : PokemonDetailsUiModel()
    object LoadingError : PokemonDetailsUiModel()
    class Types(
        val items: List<Type>
    ) : PokemonDetailsUiModel() {
        data class Type(
            val type: PokemonType.Type,
            val name: StringDesc,
            @ColorInt val color: Int
        )
    }

    data class Header(
        val text: StringDesc,
        @ColorInt val color: Int
    ) : PokemonDetailsUiModel()

    data class Measurements(
        val height: StringDesc?,
        val weight: StringDesc?
    ) : PokemonDetailsUiModel()

    data class Description(
        val text: StringDesc
    ) : PokemonDetailsUiModel()

    data class Evolution(
        val chain: EvolutionUi
    ) : PokemonDetailsUiModel()
}