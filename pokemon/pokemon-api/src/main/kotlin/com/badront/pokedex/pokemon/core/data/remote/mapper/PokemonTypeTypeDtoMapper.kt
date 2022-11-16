package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

interface PokemonTypeTypeDtoMapper {
    fun map(typeDto: NamedApiResourceDto): PokemonType.Type?
}