package com.badront.pokedex.pokemon.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDto
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import javax.inject.Inject

internal class PokemonDtoMapper @Inject constructor() {
    fun map(dto: PokemonDto): Pokemon {
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