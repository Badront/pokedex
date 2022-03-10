package com.badront.pokedex.pokemon.core.data.remote.model

import com.google.gson.annotations.SerializedName

class ListPokemonDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)