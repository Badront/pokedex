package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.model.toDesc
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import java.util.Locale
import javax.inject.Inject

internal class PokemonDetailsTypeUiMapperImpl @Inject constructor(
    private val typeColorMapper: PokemonTypeColorMapper
) : PokemonDetailsTypeUiMapper {
    override fun map(type: PokemonType): PokemonDetailsUiModel.Types.Type {
        return PokemonDetailsUiModel.Types.Type(
            type = type.type,
            name = type.type.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }.toDesc(),
            color = typeColorMapper.map(type.type)
        )
    }
}