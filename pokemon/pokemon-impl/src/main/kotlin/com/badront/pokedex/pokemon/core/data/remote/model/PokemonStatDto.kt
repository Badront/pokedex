package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokemonStatDto(
    @SerializedName("base_stat")
    val defaultValue: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: StatDto
) {
    class StatDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
    )
}