package com.badront.pokedex.pokemon.core.data.remote

import com.badront.pokedex.pokemon.core.data.remote.model.ListPokemonDto
import com.badront.pokedex.pokemon.core.data.remote.model.ListPokemonResultDto
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonsPage(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ListPokemonResultDto<ListPokemonDto>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonDetailsDto

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): PokemonDetailsDto
}