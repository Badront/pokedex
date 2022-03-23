package com.badront.pokedex.pokemon.core.domain

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.pokemon.core.domain.exception.LoadingPokemonSpeciesException
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonSpecies

interface PokemonSpeciesRepository {
    suspend fun loadSpeciesById(id: PokeId): Either<PokemonSpecies, LoadingPokemonSpeciesException>
}