package com.badront.pokedex.pokemon.evolution.domain

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.pokemon.core.domain.PokemonSpeciesRepository
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonSpeciesException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonSpecies
import javax.inject.Inject

class LoadPokemonSpecies @Inject constructor(
    private val speciesRepository: PokemonSpeciesRepository
) {
    suspend operator fun invoke(id: PokeId): Either<PokemonSpecies, LoadingPokemonSpeciesException> {
        return speciesRepository.loadSpeciesById(id)
    }
}