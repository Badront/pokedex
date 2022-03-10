package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokemonDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Long,
    @SerializedName("types")
    val types: List<PokemonTypeDto>,
    @SerializedName("stats")
    val stats: List<PokemonStatDto>,
    @SerializedName("weight")
    val weight: Int? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("sprites")
    val sprites: Map<String, String?>
)