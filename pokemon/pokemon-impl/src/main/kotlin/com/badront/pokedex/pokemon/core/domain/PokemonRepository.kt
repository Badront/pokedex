package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun loadPokemonById(id: Int): Result<DetailedPokemon, LoadingPokemonException>
    suspend fun getPokemonById(id: Int): DetailedPokemon?
    fun getPokemonByIdAsFlow(id: Int): Flow<DetailedPokemon?>
    suspend fun savePokemon(pokemon: DetailedPokemon)
    suspend fun deletePokemonById(id: Int)
}