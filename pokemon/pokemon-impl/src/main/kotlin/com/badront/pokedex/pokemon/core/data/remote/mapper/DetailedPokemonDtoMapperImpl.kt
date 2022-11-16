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

internal class DetailedPokemonDtoMapperImpl @Inject constructor(
    private val pokemonDtoMapper: PokemonDtoMapper,
    private val typeDtoMapper: PokemonTypeDtoMapper
) : DetailedPokemonDtoMapper {
    override fun map(dto: PokemonDetailsDto): DetailedPokemon {
        return DetailedPokemon(
            pokemon = Pokemon(
                id = dto.id,
                name = dto.name,
                number = dto.id,
                image = pokemonDtoMapper.pokemonImageById(dto.id)
            ),
            details = mapDetails(dto)
        )
    }

    private fun mapDetails(dto: PokemonDetailsDto): PokemonDetails {
        return PokemonDetails(
            id = dto.id,
            height = dto.height?.let {
                mapHeight(it)
            },
            weight = dto.weight?.let {
                mapWeight(it)
            },
            types = dto.types.mapNotNull { typeDtoMapper.map(it) }
        )
    }

    private fun mapHeight(height: Int): Height {
        return Height(
            value = height.toDouble(),
            uom = HeightUOM.DM
        )
    }

    private fun mapWeight(weight: Int): Weight {
        return Weight(
            value = weight.toDouble(),
            uom = WeightUOM.HG
        )
    }
}