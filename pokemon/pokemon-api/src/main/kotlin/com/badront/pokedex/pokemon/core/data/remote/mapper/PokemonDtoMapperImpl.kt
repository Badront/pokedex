package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.core.ext.url.lastUrlPathSegment
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import javax.inject.Inject

class PokemonDtoMapperImpl @Inject constructor() : PokemonDtoMapper {
    override fun map(dto: NamedApiResourceDto): Pokemon {
        val idStr = dto.url.lastUrlPathSegment()
        val id = idStr.toInt()
        return Pokemon(
            id = id,
            name = dto.name,
            number = id,
            image = pokemonImageById(id)
        )
    }

    // if default sprites needed "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    override fun pokemonImageById(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}