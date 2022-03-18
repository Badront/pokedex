package com.badront.pokedex.pokemon.details.domain

import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.PokemonRepository
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetDetailedPokemonByIdAsFlow @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(id: PokeId): Flow<PokemonDetails?> {
        return flowOf(null)
    }
}