package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {
    fun getPokemonsAsFlow(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: PokeId): Pokemon?
    suspend fun loadPokemonList(pageInfo: PageInfo): Result<Page<Pokemon>, LoadingPokemonListException>
    suspend fun savePokemonList(pokemons: List<Pokemon>)
    suspend fun replacePokemonList(pokemons: List<Pokemon>)
    suspend fun clearPokemonList()
}