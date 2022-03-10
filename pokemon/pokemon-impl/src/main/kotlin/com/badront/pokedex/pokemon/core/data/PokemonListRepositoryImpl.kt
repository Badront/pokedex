package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.ListPokemonDtoMapper
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val listPokemonDtoMapper: ListPokemonDtoMapper
) : PokemonListRepository {
    private val stateFlow = MutableStateFlow<List<ListPokemon>>(emptyList())
    override suspend fun getPokemonsAsFlow(): Flow<List<ListPokemon>> {
        return stateFlow
    }

    override suspend fun loadPokemonList(pageInfo: PageInfo): Result<Page<ListPokemon>, LoadingPokemonListException> {
        return runCatching {
            pokemonApi.getPokemonsPage(
                offset = pageInfo.offset,
                limit = pageInfo.limit
            )
        }.fold(
            onSuccess = { result ->
                Result.success(
                    Page(
                        total = result.totalCount,
                        items = result.items.map { listPokemonDtoMapper.map(it) })
                )
            },
            onFailure = { throwable ->
                Result.failure(LoadingPokemonListException(throwable))
            }
        )
    }

    override suspend fun savePokemonList(pokemons: List<ListPokemon>) {
        // TODO mutex? not if we save it to room
        stateFlow.update { oldPokemons ->
            val resultList = oldPokemons.toMutableList()
            resultList.addAll(pokemons)
            resultList
        }
    }

    override suspend fun replacePokemonList(pokemons: List<ListPokemon>) {
        stateFlow.value = pokemons
    }

    override suspend fun clearPokemonList() {
        stateFlow.value = emptyList()
    }
}