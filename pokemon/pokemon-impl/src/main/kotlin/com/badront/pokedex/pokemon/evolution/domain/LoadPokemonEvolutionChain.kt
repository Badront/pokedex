package com.badront.pokedex.pokemon.evolution.domain

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.core.model.fold
import com.badront.pokedex.core.model.map
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.core.domain.usecase.LoadEvolutionChain
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import javax.inject.Inject

class LoadPokemonEvolutionChain @Inject constructor(
    private val loadPokemonSpecies: LoadPokemonSpecies,
    private val loadEvolutionChain: LoadEvolutionChain
) {
    suspend operator fun invoke(id: PokeId): Either<EvolutionChain?, Exception> {
        return loadPokemonSpecies(id)
            .map { species ->
                species.evolutionChainId
            }
            .fold(onSuccess = { chainId ->
                if (chainId != null) {
                    loadEvolutionChain(chainId)
                } else {
                    Either.success(null)
                }
            }, onFailure = {
                Either.failure(it)
            })
    }
}