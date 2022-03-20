package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun loadPokemonById(id: PokeId): Result<DetailedPokemon, LoadingPokemonException>
    suspend fun getPokemonDetailsById(id: PokeId): PokemonDetails?
    fun getPokemonDetailsByIdAsFlow(id: PokeId): Flow<PokemonDetails?>
    suspend fun savePokemonDetails(pokemon: PokemonDetails)
    suspend fun deletePokemonDetailsById(id: PokeId)
}