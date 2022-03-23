package com.badront.pokedex.pokemon.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

class PokemonStatDto(
    @SerializedName("base_stat")
    val defaultValue: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: NamedApiResourceDto
)