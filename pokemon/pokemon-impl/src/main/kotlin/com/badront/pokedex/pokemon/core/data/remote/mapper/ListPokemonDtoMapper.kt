package com.badront.pokedex.pokemon.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.pokemon.core.data.remote.model.ListPokemonDto
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class ListPokemonDtoMapper @Inject constructor() {
    fun map(dto: ListPokemonDto): ListPokemon {
        val idStr = Uri.parse(dto.url).lastPathSegment!!
        val id = idStr.toInt()
        return ListPokemon(
            id = id,
            name = dto.name,
            number = id,
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
    }
    // todo if default sprites needed "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}