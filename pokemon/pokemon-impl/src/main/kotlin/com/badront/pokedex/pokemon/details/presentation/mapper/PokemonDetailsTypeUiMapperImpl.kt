package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.badront.pokedex.pokemon.core.widget.PokemonTypeUiModel
import javax.inject.Inject

internal class PokemonDetailsTypeUiMapperImpl @Inject constructor() : PokemonDetailsTypeUiMapper {
    override fun map(type: PokemonType): PokemonTypeUiModel {
        return PokemonTypeUiModel(
            type = type.type
        )
    }
}