package com.badront.pokedex.evolution.core.data.remote.model

import com.google.gson.annotations.SerializedName

class BabyTriggerItemDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)