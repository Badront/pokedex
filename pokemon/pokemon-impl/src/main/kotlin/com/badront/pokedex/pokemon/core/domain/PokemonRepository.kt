package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonException
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun loadPokemonById(id: PokeId): Result<DetailedPokemon, LoadingPokemonException>
    suspend fun getPokemonById(id: PokeId): DetailedPokemon?
    fun getPokemonByIdAsFlow(id: PokeId): Flow<DetailedPokemon?>
    suspend fun savePokemon(pokemon: DetailedPokemon)
    suspend fun deletePokemonById(id: PokeId)
}