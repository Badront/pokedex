package com.badront.pokedex.pokemon.core.domain.usecase

import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class LoadListPokemonsPage @Inject constructor(
    private val pokemonListRepository: PokemonListRepository
) {
    suspend operator fun invoke(offset: Int): Result<Page<ListPokemon>, LoadingPokemonListException> {
        return pokemonListRepository.loadPokemonList(
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