package com.badront.pokedex.evolution.core.data

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.evolution.core.data.remote.EvolutionApi
import com.badront.pokedex.evolution.core.data.remote.mapper.EvolutionChainDtoMapper
import com.badront.pokedex.evolution.core.domain.EvolutionChainRepository
import com.badront.pokedex.evolution.core.domain.exception.EvolutionChainLoadingException
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.core.domain.model.EvolutionChainId
import javax.inject.Inject

internal class EvolutionChainRepositoryImpl @Inject constructor(
    private val api: EvolutionApi,
    private val mapper: EvolutionChainDtoMapper
) : EvolutionChainRepository {
    override suspend fun loadChain(id: EvolutionChainId): Either<EvolutionChain, EvolutionChainLoadingException> {
        return runCatching {
            api.getEvolutionChain(id)
        }.map { dto ->
            mapper.map(dto)
        }.fold(
            onSuccess = {
                Either.success(it)
            },
            onFailure = {
                Either.failure(EvolutionChainLoadingException(it))
            }
        )
    }
}