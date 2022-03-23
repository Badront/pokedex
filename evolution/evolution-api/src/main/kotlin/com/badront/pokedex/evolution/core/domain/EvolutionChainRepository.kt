package com.badront.pokedex.evolution.core.domain

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.evolution.core.domain.exception.EvolutionChainLoadingException
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.core.domain.model.EvolutionChainId

interface EvolutionChainRepository {
    suspend fun loadChain(id: EvolutionChainId): Either<EvolutionChain, EvolutionChainLoadingException>
}