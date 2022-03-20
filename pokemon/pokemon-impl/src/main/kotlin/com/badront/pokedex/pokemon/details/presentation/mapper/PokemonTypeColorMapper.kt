package com.badront.pokedex.pokemon.details.presentation.mapper

import androidx.annotation.ColorInt
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

interface PokemonTypeColorMapper {
    @ColorInt
    fun map(type: PokemonType.Type): Int
}