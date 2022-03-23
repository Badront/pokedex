package com.badront.pokedex.pokemon.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

class PokemonTypeDto(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: NamedApiResourceDto
)