package com.badront.pokedex.pokemon.details.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.core.model.fold
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.PokemonRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadAndSaveDetailedPokemon @Inject constructor(
    private val loadDetailedPokemon: LoadDetailedPokemon,
    private val pokemonListRepository: PokemonListRepository,
    private val pokemonRepository: PokemonRepository,
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        id: PokeId
    ): Result<DetailedPokemon?, LoadingPokemonException> = withContext(appDispatchers.io) {
        val result = loadDetailedPokemon(id)
        result.fold(
            onSuccess = { detailedPokemon ->
                pokemonListRepository.saveListPokemon(detailedPokemon.pokemon)
                if (detailedPokemon.details != null) {
                    pokemonRepository.savePokemonDetails(detailedPokemon.details)
                } else {
                    pokemonRepository.deletePokemonDetailsById(id)
                }
                Result.success(detailedPokemon)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}