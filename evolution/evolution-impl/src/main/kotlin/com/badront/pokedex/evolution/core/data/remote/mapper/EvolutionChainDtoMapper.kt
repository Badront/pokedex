package com.badront.pokedex.evolution.core.data.remote.mapper

import com.badront.pokedex.evolution.core.data.remote.model.EvolutionChainDto
import com.badront.pokedex.evolution.core.data.remote.model.EvolutionChainLinkDto
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapper
import javax.inject.Inject

internal class EvolutionChainDtoMapper @Inject constructor(
    private val evolutionDetailsDtoMapper: EvolutionDetailsDtoMapper,
    private val pokemonDtoMapper: PokemonDtoMapper
) {
    fun map(dto: EvolutionChainDto): EvolutionChain {
        return map(dto.id, dto.chainLinkDto)
    }

    private fun map(chainId: Int, dto: EvolutionChainLinkDto): EvolutionChain {
        return EvolutionChain(
            id = chainId,
            details = dto.details?.map { evolutionDetailsDtoMapper.map(it) } ?: emptyList(),
            pokemon = pokemonDtoMapper.map(dto.species),
            evolvesTo = dto.evolvesTo?.map { map(chainId, it) } ?: emptyList()
        )
    }
}