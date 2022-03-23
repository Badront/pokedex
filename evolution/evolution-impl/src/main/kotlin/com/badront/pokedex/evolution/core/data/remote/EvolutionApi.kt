package com.badront.pokedex.evolution.core.data.remote

import com.badront.pokedex.evolution.core.data.remote.model.EvolutionChainDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface EvolutionApi {
    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") chainId: Int): EvolutionChainDto
}