package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.domain.model.Pokemon

interface PokemonDtoMapper {
    fun map(dto: NamedApiResourceDto): Pokemon
    fun pokemonImageById(id: Int): String
}