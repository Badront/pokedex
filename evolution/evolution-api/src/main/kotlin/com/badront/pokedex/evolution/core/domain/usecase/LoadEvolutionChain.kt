package com.badront.pokedex.evolution.core.domain.usecase

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.evolution.core.domain.EvolutionChainRepository
import com.badront.pokedex.evolution.core.domain.exception.EvolutionChainLoadingException
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.core.domain.model.EvolutionChainId
import javax.inject.Inject

class LoadEvolutionChain @Inject constructor(
    private val evolutionChainRepository: EvolutionChainRepository
) {

    suspend operator fun invoke(id: EvolutionChainId): Either<EvolutionChain, EvolutionChainLoadingException> {
        return evolutionChainRepository.loadChain(id)
    }
}