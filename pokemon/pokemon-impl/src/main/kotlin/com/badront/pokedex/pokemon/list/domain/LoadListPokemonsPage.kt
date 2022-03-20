package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadListPokemonsPage @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun invoke(
        offset: Int
    ): Result<Page<Pokemon>, LoadingPokemonListException> = withContext(appDispatchers.io) {
        pokemonListRepository.loadPokemonList(
            PageInfo(
                limit = PAGE_LIMIT,
                offset = offset
            )
        )
    }

    private companion object {
        private const val PAGE_LIMIT = 100
    }
}