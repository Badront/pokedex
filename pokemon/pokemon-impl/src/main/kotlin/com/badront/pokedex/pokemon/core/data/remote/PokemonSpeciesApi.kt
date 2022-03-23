package com.badront.pokedex.pokemon.core.data.remote

import com.badront.pokedex.pokemon.core.data.remote.model.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonSpeciesApi {
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpeciesById(
        @Path("id") id: Int
    ): PokemonSpeciesDto
}