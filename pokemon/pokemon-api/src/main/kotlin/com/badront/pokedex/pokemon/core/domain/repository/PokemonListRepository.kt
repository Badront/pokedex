package com.badront.pokedex.pokemon.core.domain.repository

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {
    fun getPokemonsAsFlow(): Flow<List<Pokemon>>
    fun getPokemonByIdAsFlow(id: PokeId): Flow<Pokemon?>
    suspend fun getPokemonById(id: PokeId): Pokemon?
    suspend fun loadPokemonList(pageInfo: PageInfo): Either<Page<Pokemon>, LoadingPokemonListException>
    suspend fun savePokemonList(pokemons: List<Pokemon>)
    suspend fun replacePokemonList(pokemons: List<Pokemon>)
    suspend fun saveListPokemon(pokemon: Pokemon)
    suspend fun clearPokemonList()
}