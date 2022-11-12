package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

interface PokemonTypeDtoMapper {
    fun map(dto: PokemonTypeDto): PokemonType?
}