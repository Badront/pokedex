package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class ListPokemonResultDto<T>(
    @SerializedName("count")
    val totalCount: Int,
    @SerializedName("next")
    val nextUrl: String? = null,
    @SerializedName("previous")
    val prevUrl: String? = null,
    @SerializedName("results")
    val items: List<T>
)