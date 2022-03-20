package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.model.measurements.Height
import com.badront.pokedex.core.model.measurements.HeightUOM
import com.badront.pokedex.core.model.measurements.Weight
import com.badront.pokedex.core.model.measurements.WeightUOM
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDetailsDto
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import javax.inject.Inject

internal class DetailedPokemonDtoMapper @Inject constructor(
    private val listPokemonDtoMapper: ListPokemonDtoMapper,
    private val typeDtoMapper: PokemonTypeDtoMapper
) {
    fun map(dto: PokemonDetailsDto): DetailedPokemon {
        return DetailedPokemon(
            pokemon = Pokemon(
                id = dto.id,
                name = dto.name,
                number = dto.id,
                image = listPokemonDtoMapper.pokemonImageById(dto.id)
            ),
            details = PokemonDetails(
                id = dto.id,
                height = dto.height?.let {
                    Height(
                        value = it.toDouble(),
                        uom = HeightUOM.DM
                    )
                },
                weight = dto.weight?.let {
                    Weight(
                        value = it.toDouble(),
                        uom = WeightUOM.HG
                    )
                },
                types = dto.types.mapNotNull { typeDtoMapper.map(it) }
            )
        )
    }
}