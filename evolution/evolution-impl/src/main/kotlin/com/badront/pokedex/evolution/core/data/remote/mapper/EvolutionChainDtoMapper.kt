package com.badront.pokedex.evolution.core.data.remote.mapper

import com.badront.pokedex.evolution.core.data.remote.model.EvolutionChainDto
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import javax.inject.Inject

internal class EvolutionChainDtoMapper @Inject constructor() {
    fun map(dto: EvolutionChainDto): EvolutionChain {
        return EvolutionChain(
            id = dto.id
        )
    }
}