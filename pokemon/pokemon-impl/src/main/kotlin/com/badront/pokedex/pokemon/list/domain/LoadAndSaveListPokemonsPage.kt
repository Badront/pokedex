package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.model.Either
import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.fold
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadAndSaveListPokemonsPage @Inject constructor(
    private val loadListPokemonsPage: LoadListPokemonsPage,
    private val pokemonListRepository: PokemonListRepository,
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        offset: Int
    ): Either<Page<Pokemon>, LoadingPokemonListException> = withContext(appDispatchers.io) {
        val result = loadListPokemonsPage(offset)
        result.fold(
            onSuccess = { page ->
                if (offset == 0) {
                    pokemonListRepository.replacePokemonList(page.items)
                } else {
                    pokemonListRepository.savePokemonList(page.items)
                }
                Either.success(page)
            },
            onFailure = {
                Either.failure(it)
            }
        )
    }
}