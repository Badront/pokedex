package com.badront.pokedex.pokemon.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonSpeciesDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonSpecies
import javax.inject.Inject

internal class PokemonSpeciesDtoMapper @Inject constructor(
    private val pokemonDtoMapper: PokemonDtoMapper
) {
    fun map(dto: PokemonSpeciesDto): PokemonSpecies {
        return PokemonSpecies(
            id = dto.id,
            name = dto.name,
            evolvesFrom = dto.evolvesFromSpecies?.let { pokemonDtoMapper.map(it) },
            evolutionChainId = dto.evolutionChain?.let { Uri.parse(it.url).lastPathSegment?.toInt() }
        )
    }
}