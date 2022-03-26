package com.badront.pokedex.pokemon.details.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.model.Either
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.repository.PokemonRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadDetailedPokemon @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        id: PokeId
    ): Either<DetailedPokemon, LoadingPokemonException> = withContext(appDispatchers.io) {
        pokemonRepository.loadPokemonById(id)
    }
}