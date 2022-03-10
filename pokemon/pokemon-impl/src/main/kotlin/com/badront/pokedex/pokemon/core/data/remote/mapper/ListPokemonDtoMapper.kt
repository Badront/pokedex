package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.pokemon.core.data.remote.model.ListPokemonDto
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class ListPokemonDtoMapper @Inject constructor() {
    fun map(dto: ListPokemonDto): ListPokemon {
        val currentUrl = dto.url.dropLastWhile { it.isDigit().not() }
        val id = currentUrl.takeLastWhile { it.isDigit() }.toLong()
        return ListPokemon(
            id = id,
            name = dto.name,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        )
    }
    // todo maybe https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png
}