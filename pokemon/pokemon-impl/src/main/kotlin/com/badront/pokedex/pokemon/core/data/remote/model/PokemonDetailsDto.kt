package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokemonDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
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