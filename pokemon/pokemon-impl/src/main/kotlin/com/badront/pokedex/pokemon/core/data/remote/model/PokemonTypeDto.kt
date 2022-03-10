package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokemonTypeDto(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeDto
) {
    class TypeDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
    )
}