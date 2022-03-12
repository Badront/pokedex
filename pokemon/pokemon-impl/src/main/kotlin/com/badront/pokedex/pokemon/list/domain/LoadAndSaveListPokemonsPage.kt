package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.core.model.fold
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class LoadAndSaveListPokemonsPage @Inject constructor(
    private val loadListPokemonsPage: LoadListPokemonsPage,
    private val listPokemonListRepository: PokemonListRepository
) {
    suspend operator fun invoke(offset: Int): Result<Page<ListPokemon>, LoadingPokemonListException> {
        val result = loadListPokemonsPage(offset)
        return result.fold(
            onSuccess = { page ->
                if (offset == 0) {
                    listPokemonListRepository.replacePokemonList(page.items)
                } else {
                    listPokemonListRepository.savePokemonList(page.items)
                }
                Result.success(page)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}