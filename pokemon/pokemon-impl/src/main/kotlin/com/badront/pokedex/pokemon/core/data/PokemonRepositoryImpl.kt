package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Result
import com.badront.pokedex.core.utils.db.DbTransactionRunner
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonDetailsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonStatsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonTypeDao
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.DetailedPokemonDtoMapper
import com.badront.pokedex.pokemon.core.domain.PokemonRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val dtoMapper: DetailedPokemonDtoMapper,
    private val entityMapper: DetailedPokemonEntityMapper,
    private val pokemonDetailsDao: PokemonDetailsDao,
    private val pokemonTypeDao: PokemonTypeDao,
    private val pokemonStatsDao: PokemonStatsDao,
    private val transactionRunner: DbTransactionRunner
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

    override suspend fun getPokemonDetailsById(id: PokeId): PokemonDetails? {
        return pokemonDetailsDao.getPokemonDetailsRelationsById(id)?.let { detailsWithRelations ->
            entityMapper.map(detailsWithRelations)
        }
    }

    override fun getPokemonDetailsByIdAsFlow(id: PokeId): Flow<PokemonDetails?> {
        return pokemonDetailsDao.getPokemonDetailsRelationsByIdAsFlow(id)
            .map { detailsWithRelations ->
                detailsWithRelations?.let { entityMapper.map(it) }
            }
    }

    override suspend fun savePokemonDetails(pokemon: PokemonDetails) {
        val detailsWithRelations = entityMapper.map(pokemon)
        transactionRunner.withTransaction {
            pokemonDetailsDao.insertOrUpdate(detailsWithRelations.details)
            pokemonTypeDao.updatePokemonTypes(pokemonId = pokemon.id, detailsWithRelations.types)
            pokemonStatsDao.updatePokemonStats(pokemonId = pokemon.id, detailsWithRelations.stats)
        }
    }

    override suspend fun deletePokemonDetailsById(id: PokeId) {
        transactionRunner.withTransaction {
            pokemonDetailsDao.deleteById(id)
            pokemonTypeDao.deleteByPokemonId(id)
            pokemonStatsDao.deleteByPokemonId(id)
        }
    }
}