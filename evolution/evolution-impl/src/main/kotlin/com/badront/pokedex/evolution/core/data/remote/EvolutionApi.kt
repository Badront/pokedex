package com.badront.pokedex.evolution.core.data.remote

import com.badront.pokedex.evolution.core.data.remote.model.EvolutionChainDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EvolutionApi {
    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: String): EvolutionChainDto
}