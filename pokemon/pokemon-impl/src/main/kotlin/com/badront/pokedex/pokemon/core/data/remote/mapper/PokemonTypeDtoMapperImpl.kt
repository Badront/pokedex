package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import javax.inject.Inject

class PokemonTypeDtoMapperImpl @Inject constructor(
    private val typeTypeDtoMapper: PokemonTypeTypeDtoMapper
) : PokemonTypeDtoMapper {
    override fun map(dto: PokemonTypeDto): PokemonType? {
        val type = typeTypeDtoMapper.map(dto.type)
        return if (type != null) {
            PokemonType(
                order = dto.slot,
                type = type
            )
        } else {
            null
        }
    }
}