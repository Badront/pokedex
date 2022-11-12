package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDetailsDto
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon

interface DetailedPokemonDtoMapper {
    fun map(dto: PokemonDetailsDto): DetailedPokemon
}