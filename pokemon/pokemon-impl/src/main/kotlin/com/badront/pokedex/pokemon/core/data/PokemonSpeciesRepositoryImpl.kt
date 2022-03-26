package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.pokemon.core.data.remote.PokemonSpeciesApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonSpeciesDtoMapper
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonSpeciesException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonSpecies
import com.badront.pokedex.pokemon.core.domain.repository.PokemonSpeciesRepository
import javax.inject.Inject

internal class PokemonSpeciesRepositoryImpl @Inject constructor(
    private val api: PokemonSpeciesApi,
    private val speciesDtoMapper: PokemonSpeciesDtoMapper
) : PokemonSpeciesRepository {
    override suspend fun loadSpeciesById(id: PokeId): Either<PokemonSpecies, LoadingPokemonSpeciesException> {
        return runCatching {
            api.getPokemonSpeciesById(id)
        }.map { dto ->
            speciesDtoMapper.map(dto)
        }.fold(
            onSuccess = {
                Either.success(it)
            },
            onFailure = {
                Either.failure(LoadingPokemonSpeciesException(it))
            }
        )
    }
}