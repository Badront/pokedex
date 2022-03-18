package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.DetailedPokemonDtoMapper
import com.badront.pokedex.pokemon.core.domain.PokemonRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val dtoMapper: DetailedPokemonDtoMapper
) : PokemonRepository {
    override suspend fun loadPokemonById(id: PokeId): Result<DetailedPokemon, LoadingPokemonException> {
        return runCatching {
            pokemonApi.getPokemonById(id)
        }.fold(
            onSuccess = { result ->
                Result.success(dtoMapper.map(result))
            },
            onFailure = { throwable ->
                Result.failure(LoadingPokemonException(throwable))
            }
        )
    }

    override suspend fun getPokemonById(id: PokeId): DetailedPokemon? {
        TODO("Not yet implemented")
    }

    override fun getPokemonByIdAsFlow(id: PokeId): Flow<DetailedPokemon?> {
        TODO("Not yet implemented")
    }

    override suspend fun savePokemon(pokemon: DetailedPokemon) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePokemonById(id: PokeId) {
        TODO("Not yet implemented")
    }
}