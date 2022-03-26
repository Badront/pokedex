package com.badront.pokedex.item.core.data.remote.mapper

import javax.inject.Inject

class ItemImageDtoMapper @Inject constructor() {
    fun map(name: String): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/$name.png"
    }
}