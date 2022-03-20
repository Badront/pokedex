package com.badront.pokedex.pokemon.details.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.PokemonRepository
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonDetailsByIdAsFlow @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val pokemonRepository: PokemonRepository,
    private val appDispatchers: AppDispatchers
) {
    operator fun invoke(id: PokeId): Flow<DetailedPokemon?> {
        return combine(
            pokemonListRepository.getPokemonByIdAsFlow(id),
            pokemonRepository.getPokemonDetailsByIdAsFlow(id)
        ) { pokemon, pokemonDetails ->
            if (pokemon == null) {
                null
            } else {
                DetailedPokemon(
                    pokemon = pokemon,
                    details = pokemonDetails
                )
            }
        }
            .flowOn(appDispatchers.io)
    }
}