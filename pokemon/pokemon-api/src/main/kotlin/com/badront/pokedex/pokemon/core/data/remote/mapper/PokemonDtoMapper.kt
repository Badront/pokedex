package com.badront.pokedex.pokemon.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import javax.inject.Inject

class PokemonDtoMapper @Inject constructor() {
    fun map(dto: NamedApiResourceDto): Pokemon {
        val idStr = Uri.parse(dto.url).lastPathSegment!!
        val id = idStr.toInt()
        return Pokemon(
            id = id,
            name = dto.name,
            number = id,
            image = pokemonImageById(id)
        )
    }

    // if default sprites needed "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    fun pokemonImageById(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}