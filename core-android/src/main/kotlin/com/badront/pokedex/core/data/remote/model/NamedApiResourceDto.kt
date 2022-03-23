package com.badront.pokedex.core.data.remote.model

import com.google.gson.annotations.SerializedName

class NamedApiResourceDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)