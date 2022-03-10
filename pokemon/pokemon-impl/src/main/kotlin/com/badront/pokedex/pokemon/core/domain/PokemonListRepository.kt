package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.Result
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonListException
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {
    suspend fun getPokemonsAsFlow(): Flow<List<ListPokemon>>
    suspend fun loadPokemonList(pageInfo: PageInfo): Result<Page<ListPokemon>, LoadingPokemonListException>
    suspend fun savePokemonList(pokemons: List<ListPokemon>)
    suspend fun replacePokemonList(pokemons: List<ListPokemon>)
    suspend fun clearPokemonList()
}