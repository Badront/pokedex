package com.badront.pokedex.pokemon.list.domain

import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class GetPokemonById @Inject constructor(
    private val pokemonListRepository: PokemonListRepository
) {
    suspend operator fun invoke(id: Int): ListPokemon? {
        return pokemonListRepository.getPokemonById(id)
    }
}