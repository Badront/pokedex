package com.badront.pokedex.pokemon.core.domain.repository

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun loadPokemonById(id: PokeId): Either<DetailedPokemon, LoadingPokemonException>
    suspend fun getPokemonDetailsById(id: PokeId): PokemonDetails?
    fun getPokemonDetailsByIdAsFlow(id: PokeId): Flow<PokemonDetails?>
    suspend fun savePokemonDetails(pokemon: PokemonDetails)
    suspend fun deletePokemonDetailsById(id: PokeId)
}