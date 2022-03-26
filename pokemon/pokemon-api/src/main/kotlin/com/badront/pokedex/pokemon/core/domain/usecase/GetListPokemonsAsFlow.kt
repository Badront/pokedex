package com.badront.pokedex.pokemon.core.domain.usecase

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListPokemonsAsFlow @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val appDispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonListRepository.getPokemonsAsFlow()
            .flowOn(appDispatchers.io)
    }
}