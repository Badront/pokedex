package com.badront.pokedex.item.core.data.remote.model

import com.google.gson.annotations.SerializedName

class ItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)