package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokemonById @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun invoke(id: PokeId): Pokemon? {
        return withContext(appDispatchers.io) {
            pokemonListRepository.getPokemonById(id)
        }
    }
}