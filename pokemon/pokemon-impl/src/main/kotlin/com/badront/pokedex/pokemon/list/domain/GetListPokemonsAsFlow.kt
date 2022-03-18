package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPokemonsAsFlow @Inject constructor(
    private val pokemonListRepository: PokemonListRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonListRepository.getPokemonsAsFlow()
    }
}