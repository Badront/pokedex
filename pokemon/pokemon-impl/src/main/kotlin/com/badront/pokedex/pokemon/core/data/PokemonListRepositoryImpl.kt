package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.database.pokemon.dao.ListPokemonDao
import com.badront.pokedex.pokemon.core.data.local.mapper.ListPokemonEntityMapper
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapper
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException
import javax.inject.Inject

internal class PokemonListRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val listPokemonDao: ListPokemonDao,
    private val pokemonDtoMapper: PokemonDtoMapper,
    private val listPokemonEntityMapper: ListPokemonEntityMapper
) : PokemonListRepository {
    override fun getPokemonsAsFlow(): Flow<List<Pokemon>> {
        return listPokemonDao
            .getAllAsFlow()
            .map { entities ->
                entities.map { listPokemonEntityMapper.map(it) }
            }
    }

    override fun getPokemonByIdAsFlow(id: PokeId): Flow<Pokemon?> {
        return listPokemonDao
            .getByIdAsFlow(id)
            .map { pokemonEntity ->
                pokemonEntity?.let { listPokemonEntityMapper.map(it) }
            }
    }

    override suspend fun getPokemonById(id: PokeId): Pokemon? {
        return listPokemonDao
            .getById(id)?.let {
                listPokemonEntityMapper.map(it)
            }
    }

    override suspend fun loadPokemonList(pageInfo: PageInfo): Either<Page<Pokemon>, LoadingPokemonListException> {
        return runCatching {
            pokemonApi.getPokemonsPage(
                offset = pageInfo.offset,
                limit = pageInfo.limit
            )
        }.fold(
            onSuccess = { result ->
                Either.success(
                    Page(
                        total = result.totalCount,
                        items = result.items.map { pokemonDtoMapper.map(it) })
                )
            },
            onFailure = { throwable ->
                if (throwable is CancellationException) {
                    throw throwable
                } else {
                    Either.failure(LoadingPokemonListException(throwable))
                }
            }
        )
    }

    override suspend fun savePokemonList(pokemons: List<Pokemon>) {
        listPokemonDao.insert(pokemons.map { listPokemonEntityMapper.map(it) })
    }

    override suspend fun replacePokemonList(pokemons: List<Pokemon>) {
        listPokemonDao.replaceAll(pokemons.map { listPokemonEntityMapper.map(it) })
    }

    override suspend fun saveListPokemon(pokemon: Pokemon) {
        listPokemonDao.insert(listPokemonEntityMapper.map(pokemon))
    }

    override suspend fun clearPokemonList() {
        listPokemonDao.deleteAll()
    }
}